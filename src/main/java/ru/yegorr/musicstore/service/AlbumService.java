package ru.yegorr.musicstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.request.ChangeAlbumRequestDto;
import ru.yegorr.musicstore.dto.request.CreateAlbumRequestDto;
import ru.yegorr.musicstore.dto.response.AlbumResponseDto;
import ru.yegorr.musicstore.dto.response.BriefAlbumDescriptionDto;
import ru.yegorr.musicstore.exception.ApplicationException;

import java.util.List;

public interface AlbumService {
    AlbumResponseDto createAlbum(CreateAlbumRequestDto createAlbumRequest, Long musicianId) throws ApplicationException;

    AlbumResponseDto changeAlbum(ChangeAlbumRequestDto changeAlbumRequest, Long albumId) throws ApplicationException;

    AlbumResponseDto getAlbum(Long albumId, Long userId) throws ApplicationException;

    void deleteAlbum(Long albumId) throws ApplicationException;

    byte[] getCover(Long albumId) throws ApplicationException;

    void saveCover(Long albumId, MultipartFile cover) throws ApplicationException;

    List<BriefAlbumDescriptionDto> searchAlbums(String query) throws ApplicationException;
}
