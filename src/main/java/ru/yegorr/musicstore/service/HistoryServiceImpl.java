package ru.yegorr.musicstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yegorr.musicstore.dto.response.FullTrackDto;
import ru.yegorr.musicstore.entity.FavouriteEntity;
import ru.yegorr.musicstore.entity.HistoryEntity;
import ru.yegorr.musicstore.entity.TrackEntity;
import ru.yegorr.musicstore.repository.FavouriteRepository;
import ru.yegorr.musicstore.repository.HistoryRepository;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;

    private final FavouriteRepository favouriteRepository;

    @Autowired
    public HistoryServiceImpl(HistoryRepository historyRepository, FavouriteRepository favouriteRepository) {
        this.historyRepository = historyRepository;
        this.favouriteRepository = favouriteRepository;
    }

    @Override
    @Transactional
    public List<FullTrackDto> getHistory(Long userId) {
        List<TrackEntity> tracks = historyRepository.findTop50ByUserIdOrderByPlayTimeDesc(userId).stream().
                map(HistoryEntity::getTrack).
                collect(Collectors.toList());
        Set<TrackEntity> favourites = favouriteRepository.findAllByUserIdAndTrackIn(userId, tracks).stream().
                map(FavouriteEntity::getTrack).
                collect(Collectors.toSet());
        return tracks.stream().map(trackEntity -> {
            FullTrackDto dto = new FullTrackDto();
            dto.setId(trackEntity.getTrackId());
            dto.setName(trackEntity.getName());
            dto.setPlaysNumber(trackEntity.getPlaysNumber());
            dto.setAlbumId(trackEntity.getAlbum().getAlbumId());
            dto.setAlbumName(trackEntity.getAlbum().getName());
            dto.setMusicianId(trackEntity.getAlbum().getMusician().getMusicianId());
            dto.setMusicianName(trackEntity.getAlbum().getMusician().getName());
            dto.setFavourite(favourites.contains(trackEntity));
            return dto;
        }).collect(Collectors.toList());
    }
}
