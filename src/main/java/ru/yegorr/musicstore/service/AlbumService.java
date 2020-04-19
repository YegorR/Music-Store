package ru.yegorr.musicstore.service;

import ru.yegorr.musicstore.dto.request.CreateAlbumRequest;
import ru.yegorr.musicstore.dto.response.AlbumResponseDto;
import ru.yegorr.musicstore.exception.ApplicationException;

public interface AlbumService {
    AlbumResponseDto createAlbum(CreateAlbumRequest createAlbumRequest) throws ApplicationException;
}
