package ru.yegorr.musicstore.service;

import ru.yegorr.musicstore.dto.response.MusicianResponseDto;
import ru.yegorr.musicstore.exception.ApplicationException;

public interface MusicianService {
    MusicianResponseDto createMusician(String name, String description) throws ApplicationException;

    MusicianResponseDto changeMusician(Long id, String name, String description) throws ApplicationException;

    void deleteMusician(Long id) throws ApplicationException;

    MusicianResponseDto getMusician(Long id) throws ApplicationException;
}
