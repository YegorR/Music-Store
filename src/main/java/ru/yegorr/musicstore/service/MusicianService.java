package ru.yegorr.musicstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.response.BriefMusicianDto;
import ru.yegorr.musicstore.dto.response.MusicianDto;
import ru.yegorr.musicstore.dto.response.MusicianLetterDto;
import ru.yegorr.musicstore.exception.ClientException;
import ru.yegorr.musicstore.exception.ServerException;

import java.util.List;
import java.util.Map;

public interface MusicianService {
  MusicianDto createMusician(String name, String description);

  MusicianDto changeMusician(Long id, String name, String description) throws ClientException;

  void deleteMusician(Long id) throws ClientException;

  MusicianDto getMusician(Long id) throws ClientException;

  Map<String, Integer> getMusicianCountByAbc();

  List<MusicianLetterDto> getMusiciansByLetter(String letter);

  void saveImage(Long musicianId, MultipartFile image) throws ServerException, ClientException;

  byte[] getImage(Long musicianId) throws ClientException;

  List<BriefMusicianDto> searchMusicians(String query);
}
