package ru.yegorr.musicstore.service;

import ru.yegorr.musicstore.dto.response.TrackWithPlaysNumberDto;
import ru.yegorr.musicstore.exception.ClientException;

import java.util.List;

public interface FavouriteService {
    void setFavourite(Long userId, Long trackId, boolean isFavourite) throws ClientException;

    List<TrackWithPlaysNumberDto> getFavouriteList(Long userId);
}
