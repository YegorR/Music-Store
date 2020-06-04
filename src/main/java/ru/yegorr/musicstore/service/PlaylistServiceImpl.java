package ru.yegorr.musicstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.request.IdDto;
import ru.yegorr.musicstore.dto.response.PlaylistBriefDto;
import ru.yegorr.musicstore.dto.response.PlaylistResponseDto;
import ru.yegorr.musicstore.entity.*;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.exception.ForbiddenException;
import ru.yegorr.musicstore.exception.ResourceIsNotFoundException;
import ru.yegorr.musicstore.repository.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;

    private final FavouriteRepository favouriteRepository;

    private TrackRepository trackRepository;

    private PlaylistTrackRepository playlistTrackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository, FavouriteRepository favouriteRepository) {
        this.playlistRepository = playlistRepository;
        this.favouriteRepository = favouriteRepository;
    }

    private PlaylistResponseDto getPlaylistWithFavourites(Long userId, PlaylistEntity playlistEntity) {
        List<TrackEntity> tracks = playlistEntity.getTracks().stream().
                map(PlaylistTrackEntity::getTrack).collect(Collectors.toList());
        Set<TrackEntity> favourites = favouriteRepository.findAllByUserIdAndTrackIn(userId, tracks).stream().
                map(FavouriteEntity::getTrack).collect(Collectors.toSet());
        List<Boolean> isFavourite = new ArrayList<>();
        tracks.forEach((track) -> isFavourite.add(favourites.contains(track)));

        return new PlaylistResponseDto(playlistEntity, isFavourite.iterator());
    }

    @Override
    public PlaylistResponseDto getPlaylist(Long userId, Long playlistId) throws ApplicationException {
        PlaylistEntity playlistEntity = playlistRepository.
                findById(playlistId).
                orElseThrow(() -> new ResourceIsNotFoundException("The playlist is not exists"));

        return getPlaylistWithFavourites(userId, playlistEntity);
    }

    @Override
    @Transactional
    public PlaylistResponseDto createPlaylist(Long userId, String name) throws ApplicationException {

        PlaylistEntity playlistEntity = new PlaylistEntity();
        playlistEntity.setUserId(userId);
        playlistEntity.setName(name);

        playlistEntity = playlistRepository.save(playlistEntity);

        UserEntity user = userRepository.findById(userId).
                orElseThrow(() -> new ApplicationContextException("Some error"));

        return new PlaylistResponseDto(playlistEntity, user.getNickname());
    }

    @Override
    @Transactional
    public PlaylistResponseDto changePlaylist(Long userId, Long playlistId, String name, List<IdDto> tracks) throws ApplicationException {
        PlaylistEntity playlistEntity = playlistRepository.
                findById(playlistId).
                orElseThrow(() -> new ResourceIsNotFoundException("The playlist is not exists"));

        playlistEntity.setName(name);
        if (!playlistEntity.getUserId().equals(userId)) {
            throw new ForbiddenException("You have no rights for this");
        }
        List<TrackEntity> trackEntities = new ArrayList<>();
        for (IdDto track : tracks) {
            trackEntities.add(trackRepository.findById(track.getId()).
                    orElseThrow(() -> new ResourceIsNotFoundException("The track is not exists")));
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
    public void deletePlaylist(Long userId, Long playlistId) throws ApplicationException {
        PlaylistEntity playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new ResourceIsNotFoundException("The playlist is not exists"));
        if (!playlist.getUserId().equals(userId)) {
            throw new ForbiddenException("You have no rights for this");
        }
        playlistRepository.deleteById(playlistId);
    }

    @Override
    @Transactional
    public void unloadPlaylistImage(Long userId, Long playlistId, MultipartFile image) throws ApplicationException {
        try {
            PlaylistEntity playlist = playlistRepository.findById(playlistId).
                    orElseThrow(() -> new ResourceIsNotFoundException("The playlists is not exists"));
            if (!playlist.getUserId().equals(userId)) {
                throw new ForbiddenException("You have no rights for this");
            }
            playlist.setImage(image.getBytes());
        } catch (IOException ex) {
            throw new ApplicationException("Some error", ex);
        }
    }

    @Override
    @Transactional
    public byte[] getPlaylistImage(Long playlistId) throws ApplicationException {
        return playlistRepository.findById(playlistId).
                orElseThrow(() -> new ResourceIsNotFoundException("The musician is not exists")).
                getImage();
    }

    @Override
    @Transactional
    public List<PlaylistBriefDto> searchPlaylists(String query) throws ApplicationException {
        return playlistRepository.findAllByNameContainingIgnoreCase(query).stream().
                map(PlaylistBriefDto::new).
                collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<PlaylistBriefDto> getPlaylistsOfUser(Long userId) throws ApplicationException {
        return playlistRepository.findAllByUserId(userId).stream().
                map(PlaylistBriefDto::new).
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
}
