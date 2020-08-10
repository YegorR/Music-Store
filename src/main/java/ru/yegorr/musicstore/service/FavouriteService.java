package ru.yegorr.musicstore.service;

import ru.yegorr.musicstore.dto.response.TrackFullResponseDto;
import ru.yegorr.musicstore.exception.ClientException;

import java.util.List;

public interface FavouriteService {
    void setFavourite(Long userId, Long trackId, boolean isFavourite) throws ClientException;

    List<TrackFullResponseDto> getFavouriteList(Long userId);
}
