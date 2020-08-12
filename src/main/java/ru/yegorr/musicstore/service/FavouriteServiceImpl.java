package ru.yegorr.musicstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yegorr.musicstore.dto.response.TrackWithPlaysNumberDto;
import ru.yegorr.musicstore.entity.FavouriteEntity;
import ru.yegorr.musicstore.entity.TrackEntity;
import ru.yegorr.musicstore.exception.ClientException;
import ru.yegorr.musicstore.repository.FavouriteRepository;
import ru.yegorr.musicstore.repository.TrackRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavouriteServiceImpl implements FavouriteService {

  private final FavouriteRepository favouriteRepository;

  private final TrackRepository trackRepository;

  @Autowired
  public FavouriteServiceImpl(FavouriteRepository favouriteRepository,
                              TrackRepository trackRepository) {
    this.favouriteRepository = favouriteRepository;
    this.trackRepository = trackRepository;
  }

  @Override
  @Transactional
  public void setFavourite(Long userId, Long trackId, boolean isFavourite) throws ClientException {
    if (!trackRepository.existsById(trackId)) {
      throw new ClientException(HttpStatus.NOT_FOUND, "The track is not exist");
    }
    if (isFavourite) {
      favouriteRepository.findByUserIdAndTrackId(userId, trackId).ifPresentOrElse((entity) -> {
      }, () -> {
        FavouriteEntity favouriteEntity = new FavouriteEntity();
        favouriteEntity.setAddingTime(LocalDateTime.now());
        favouriteEntity.setTrackId(trackId);
        favouriteEntity.setUserId(userId);
        favouriteRepository.save(favouriteEntity);
      });
    } else {
      favouriteRepository.deleteByUserIdAndTrackId(userId, trackId);
    }
  }

  @Override
  @Transactional
  public List<TrackWithPlaysNumberDto> getFavouriteList(Long userId) {
    return favouriteRepository.findAllByUserIdOrderByAddingTimeDesc(userId).stream().
            map((favouriteEntity) -> {
              TrackEntity track = favouriteEntity.getTrack();
              TrackWithPlaysNumberDto response = new TrackWithPlaysNumberDto();
              response.setName(track.getName());
              response.setId(track.getTrackId());
              response.setAlbumId(track.getAlbum().getAlbumId());
              response.setAlbumName(track.getAlbum().getName());
              response.setMusicianId(track.getAlbum().getMusician().getMusicianId());
              response.setMusicianName(track.getAlbum().getMusician().getName());
              response.setPlaysNumber(track.getPlaysNumber());
              return response;
            }).collect(Collectors.toList());
  }
}
