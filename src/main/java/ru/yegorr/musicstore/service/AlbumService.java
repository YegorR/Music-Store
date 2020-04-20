package ru.yegorr.musicstore.service;

import ru.yegorr.musicstore.dto.request.ChangeAlbumRequestDto;
import ru.yegorr.musicstore.dto.request.CreateAlbumRequestDto;
import ru.yegorr.musicstore.dto.response.AlbumResponseDto;
import ru.yegorr.musicstore.exception.ApplicationException;

public interface AlbumService {
    AlbumResponseDto createAlbum(CreateAlbumRequestDto createAlbumRequest, Long musicianId) throws ApplicationException;

    AlbumResponseDto changeAlbum(ChangeAlbumRequestDto changeAlbumRequest, Long albumId) throws ApplicationException;

    AlbumResponseDto getAlbum(Long albumId) throws ApplicationException;

    void deleteAlbum(Long albumId) throws ApplicationException;
}
