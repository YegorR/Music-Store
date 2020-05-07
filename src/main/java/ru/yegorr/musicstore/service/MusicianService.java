package ru.yegorr.musicstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.response.MusicianLetterResponseDto;
import ru.yegorr.musicstore.dto.response.MusicianResponseDto;
import ru.yegorr.musicstore.exception.ApplicationException;

import java.util.List;
import java.util.Map;

public interface MusicianService {
    MusicianResponseDto createMusician(String name, String description) throws ApplicationException;

    MusicianResponseDto changeMusician(Long id, String name, String description) throws ApplicationException;

    void deleteMusician(Long id) throws ApplicationException;

    MusicianResponseDto getMusician(Long id) throws ApplicationException;

    Map<String, Integer> getMusicianCountByAbc() throws ApplicationException;

    List<MusicianLetterResponseDto> getMusiciansByLetter(String letter) throws ApplicationException;

    void saveImage(Long musicianId, MultipartFile image) throws ApplicationException;

    byte[] getImage(Long musicianId) throws ApplicationException;
}
