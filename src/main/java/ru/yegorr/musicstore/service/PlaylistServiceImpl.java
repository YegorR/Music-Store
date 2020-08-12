package ru.yegorr.musicstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.request.IdDto;
import ru.yegorr.musicstore.dto.response.BriefPlaylistDto;
import ru.yegorr.musicstore.dto.response.PlaylistDto;
import ru.yegorr.musicstore.entity.*;
import ru.yegorr.musicstore.exception.ClientException;
import ru.yegorr.musicstore.exception.ServerException;
import ru.yegorr.musicstore.exception.SuddenException;
import ru.yegorr.musicstore.repository.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class PlaylistServiceImpl implements PlaylistService {

  private PlaylistRepository playlistRepository;

  private FavouriteRepository favouriteRepository;

  private TrackRepository trackRepository;

  private PlaylistTrackRepository playlistTrackRepository;

  private UserRepository userRepository;

  private PlaylistDto getPlaylistWithFavourites(Long userId, PlaylistEntity playlistEntity) {
    List<TrackEntity> tracks = playlistEntity.getTracks().stream().
            map(PlaylistTrackEntity::getTrack).
            collect(Collectors.toList());
    Set<TrackEntity> favourites = favouriteRepository.
            findAllByUserIdAndTrackIn(userId, tracks).
            stream().
            map(FavouriteEntity::getTrack).
            collect(Collectors.toSet());
    List<Boolean> isFavourite = new ArrayList<>();
    tracks.forEach((track) -> isFavourite.add(favourites.contains(track)));

    return new PlaylistDto(playlistEntity, isFavourite.iterator());
  }

  @Override
  public PlaylistDto getPlaylist(Long userId, Long playlistId) throws ClientException {
    PlaylistEntity playlistEntity = playlistRepository.
            findById(playlistId).
            orElseThrow(() -> new ClientException(HttpStatus.NOT_FOUND,
                    "The playlist is not exists"));

    return getPlaylistWithFavourites(userId, playlistEntity);
  }

  @Override
  @Transactional
  public PlaylistDto createPlaylist(Long userId, String name) {

    PlaylistEntity playlistEntity = new PlaylistEntity();
    playlistEntity.setUserId(userId);
    playlistEntity.setName(name);

    playlistEntity = playlistRepository.save(playlistEntity);

    UserEntity user = userRepository.findById(userId).
            orElseThrow(() -> new SuddenException("The user should exist!"));

    return new PlaylistDto(playlistEntity, user.getNickname());
  }

  @Override
  @Transactional
  public PlaylistDto changePlaylist(Long userId, Long playlistId, String name, List<IdDto> tracks)
          throws ClientException {
    PlaylistEntity playlistEntity = playlistRepository.
            findById(playlistId).
            orElseThrow(() -> new ClientException(HttpStatus.NOT_FOUND,
                    "The playlist is not exists"));

    playlistEntity.setName(name);
    if (!playlistEntity.getUserId().equals(userId)) {
      throw new ClientException(HttpStatus.FORBIDDEN);
    }
    List<TrackEntity> trackEntities = new ArrayList<>();
    for (IdDto track : tracks) {
      trackEntities.add(trackRepository.findById(track.getId()).
              orElseThrow(() -> new ClientException(HttpStatus.NOT_FOUND,
                      "The playlist is not exists")));
    }
    playlistTrackRepository.deleteAll(playlistEntity.getTracks());
    playlistTrackRepository.flush();

    AtomicReference<Integer> order = new AtomicReference<>(0);
    List<PlaylistTrackEntity> playlistTrackEntities = trackEntities.stream().map((entity) -> {
      PlaylistTrackEntity playlistTrackEntity = new PlaylistTrackEntity();
      playlistTrackEntity.setPlaylist(playlistEntity);
      playlistTrackEntity.setTrack(entity);
      playlistTrackEntity.setOrder(order.getAndSet(order.get() + 1));
      return playlistTrackEntity;
    }).collect(Collectors.toList());
    playlistTrackRepository.saveAll(playlistTrackEntities);
    playlistEntity.setTracks(playlistTrackEntities);

    return getPlaylistWithFavourites(userId, playlistEntity);
  }

  @Override
  @Transactional
  public void deletePlaylist(Long userId, Long playlistId) throws ClientException {
    PlaylistEntity playlist = playlistRepository.findById(playlistId).orElseThrow(() ->
            new ClientException(HttpStatus.NOT_FOUND, "The playlist is not exists"));
    if (!playlist.getUserId().equals(userId)) {
      throw new ClientException(HttpStatus.FORBIDDEN);
    }
    playlistRepository.deleteById(playlistId);
  }

  @Override
  @Transactional
  public void unloadPlaylistImage(Long userId, Long playlistId, MultipartFile image)
          throws ServerException {
    try {
      PlaylistEntity playlist = playlistRepository.findById(playlistId).
              orElseThrow(() -> new ClientException(HttpStatus.NOT_FOUND,
                      "The playlists is not exists"));
      if (!playlist.getUserId().equals(userId)) {
        throw new ClientException(HttpStatus.FORBIDDEN);
      }
      playlist.setImage(image.getBytes());
    } catch (IOException ex) {
      throw new ServerException(ex);
    }
  }

  @Override
  @Transactional
  public byte[] getPlaylistImage(Long playlistId) throws ClientException {
    return playlistRepository.findById(playlistId).
            orElseThrow(() -> new ClientException(HttpStatus.NOT_FOUND,
                    "The musician is not exists")).
            getImage();
  }

  @Override
  @Transactional
  public List<BriefPlaylistDto> searchPlaylists(String query) {
    return playlistRepository.findAllByNameContainingIgnoreCase(query).stream().
            map(BriefPlaylistDto::new).
            collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<BriefPlaylistDto> getPlaylistsOfUser(Long userId) {
    return playlistRepository.findAllByUserId(userId).stream().
            map(BriefPlaylistDto::new).
            collect(Collectors.toList());
  }

  @Autowired
  public void setTrackRepository(TrackRepository trackRepository) {
    this.trackRepository = trackRepository;
  }

  @Autowired
  public void setPlaylistTrackRepository(PlaylistTrackRepository playlistTrackRepository) {
    this.playlistTrackRepository = playlistTrackRepository;
  }

  @Autowired
  public void setPlaylistRepository(PlaylistRepository playlistRepository) {
    this.playlistRepository = playlistRepository;
  }

  @Autowired
  public void setFavouriteRepository(FavouriteRepository favouriteRepository) {
    this.favouriteRepository = favouriteRepository;
  }

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
}
