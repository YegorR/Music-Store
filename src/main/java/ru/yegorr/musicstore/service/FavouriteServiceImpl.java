package ru.yegorr.musicstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yegorr.musicstore.dto.response.TrackFullResponseDto;
import ru.yegorr.musicstore.entity.FavouriteEntity;
import ru.yegorr.musicstore.entity.TrackEntity;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.repository.FavouriteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavouriteServiceImpl implements FavouriteService {

    private final FavouriteRepository favouriteRepository;

    @Autowired
    public FavouriteServiceImpl(FavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }

    @Override
    @Transactional
    public void setFavourite(Long userId, Long trackId, boolean isFavourite) throws ApplicationException {
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
    public List<TrackFullResponseDto> getFavouriteList(Long userId) throws ApplicationException {
        return favouriteRepository.findAllByUserId(userId).stream().map((favouriteEntity) -> {
            TrackEntity track = favouriteEntity.getTrack();
            TrackFullResponseDto response = new TrackFullResponseDto();
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
