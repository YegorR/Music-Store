package ru.yegorr.musicstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.request.IdDto;
import ru.yegorr.musicstore.dto.response.GenreBriefResponseDto;
import ru.yegorr.musicstore.dto.response.GenreFullResponseDto;
import ru.yegorr.musicstore.exception.ApplicationException;

import java.util.List;

public interface GenreService {
    List<GenreBriefResponseDto> getGenresByTrack(Long trackId) throws ApplicationException;

    List<GenreBriefResponseDto> changeGenresOfTrack(Long trackId, List<IdDto> genresId) throws ApplicationException;

    GenreFullResponseDto getGenre(Long genreId) throws ApplicationException;

    List<GenreFullResponseDto> getAllGenres() throws ApplicationException;

    byte[] getImage(Long genreId) throws ApplicationException;

    void saveImage(Long genreId, MultipartFile image) throws ApplicationException;

    List<GenreBriefResponseDto> searchGenres(String query) throws ApplicationException;

    GenreFullResponseDto createGenre(String name, String description) throws ApplicationException;

    GenreFullResponseDto changeGenre(String name, String description, Long genreId) throws ApplicationException;

    void deleteGenre(Long genreId) throws ApplicationException;
}
