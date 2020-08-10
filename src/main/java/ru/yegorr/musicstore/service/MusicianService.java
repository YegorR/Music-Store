package ru.yegorr.musicstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.response.MusicianBriefResponseDto;
import ru.yegorr.musicstore.dto.response.MusicianLetterResponseDto;
import ru.yegorr.musicstore.dto.response.MusicianResponseDto;
import ru.yegorr.musicstore.exception.ClientException;
import ru.yegorr.musicstore.exception.ServerException;

import java.util.List;
import java.util.Map;

public interface MusicianService {
    MusicianResponseDto createMusician(String name, String description);

    MusicianResponseDto changeMusician(Long id, String name, String description) throws ClientException;

    void deleteMusician(Long id) throws ClientException;

    MusicianResponseDto getMusician(Long id) throws ClientException;

    Map<String, Integer> getMusicianCountByAbc();

    List<MusicianLetterResponseDto> getMusiciansByLetter(String letter);

    void saveImage(Long musicianId, MultipartFile image) throws ServerException, ClientException;

    byte[] getImage(Long musicianId) throws ClientException;

    List<MusicianBriefResponseDto> searchMusicians(String query);
}
