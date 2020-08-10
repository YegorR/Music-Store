package ru.yegorr.musicstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.request.ChangeAlbumRequestDto;
import ru.yegorr.musicstore.dto.request.CreateAlbumRequestDto;
import ru.yegorr.musicstore.dto.response.AlbumResponseDto;
import ru.yegorr.musicstore.dto.response.BriefAlbumDescriptionDto;
import ru.yegorr.musicstore.exception.ClientException;
import ru.yegorr.musicstore.exception.ServerException;

import java.util.List;

public interface AlbumService {
    AlbumResponseDto createAlbum(CreateAlbumRequestDto createAlbumRequest, Long musicianId) throws ClientException;

    AlbumResponseDto changeAlbum(ChangeAlbumRequestDto changeAlbumRequest, Long albumId) throws ClientException;

    AlbumResponseDto getAlbum(Long albumId, Long userId) throws ClientException;

    void deleteAlbum(Long albumId) throws ClientException;

    byte[] getCover(Long albumId) throws ClientException;

    void saveCover(Long albumId, MultipartFile cover) throws ClientException, ServerException;

    List<BriefAlbumDescriptionDto> searchAlbums(String query);
}
