package ru.yegorr.musicstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.request.ChangeAlbumDto;
import ru.yegorr.musicstore.dto.request.CreateAlbumDto;
import ru.yegorr.musicstore.dto.response.BriefAlbumDescriptionDto;
import ru.yegorr.musicstore.dto.response.FullAlbumDto;
import ru.yegorr.musicstore.exception.ClientException;
import ru.yegorr.musicstore.exception.ServerException;

import java.util.List;

public interface AlbumService {
  FullAlbumDto createAlbum(CreateAlbumDto createAlbumRequest, Long musicianId)
          throws ClientException;

  FullAlbumDto changeAlbum(ChangeAlbumDto changeAlbumRequest, Long albumId)
          throws ClientException;

  FullAlbumDto getAlbum(Long albumId, Long userId) throws ClientException;

  void deleteAlbum(Long albumId) throws ClientException;

  byte[] getCover(Long albumId) throws ClientException;

  void saveCover(Long albumId, MultipartFile cover) throws ServerException;

  List<BriefAlbumDescriptionDto> searchAlbums(String query);
}
