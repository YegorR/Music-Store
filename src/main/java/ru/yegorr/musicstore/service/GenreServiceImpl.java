package ru.yegorr.musicstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.request.IdDto;
import ru.yegorr.musicstore.dto.response.GenreBriefResponseDto;
import ru.yegorr.musicstore.dto.response.GenreFullResponseDto;
import ru.yegorr.musicstore.entity.GenreEntity;
import ru.yegorr.musicstore.entity.TrackEntity;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.exception.ResourceIsNotFoundException;
import ru.yegorr.musicstore.repository.GenreRepository;
import ru.yegorr.musicstore.repository.TrackRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final TrackRepository trackRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository, TrackRepository trackRepository) {
        this.genreRepository = genreRepository;
        this.trackRepository = trackRepository;
    }

    @Override
    public List<GenreBriefResponseDto> getGenresByTrack(Long trackId) throws ApplicationException {
        if (!trackRepository.existsById(trackId)) {
            throw new ResourceIsNotFoundException("The track is not exists");
        }

        return genreRepository.findByTracks_trackId(trackId).
                stream().
                map(GenreBriefResponseDto::new).
                collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<GenreBriefResponseDto> changeGenresOfTrack(Long trackId, List<IdDto> genresId) throws ApplicationException {
        List<GenreEntity> genres = genreRepository.
                findAllById(genresId.stream().
                        map(IdDto::getId).
                        collect(Collectors.toList()));
        TrackEntity trackEntity = trackRepository.findById(trackId).
                orElseThrow(() -> new ResourceIsNotFoundException("The track is not exists"));

        trackEntity.setGenres(genres);

        return genres.stream().map(GenreBriefResponseDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GenreFullResponseDto getGenre(Long genreId) throws ApplicationException {
        GenreEntity genre = genreRepository.findById(genreId).
                orElseThrow(() -> new ResourceIsNotFoundException("The genre is not exists"));
        return new GenreFullResponseDto(genre);
    }

    @Override
    @Transactional
    public List<GenreFullResponseDto> getAllGenres() throws ApplicationException {
        return genreRepository.findAll().stream().map(GenreFullResponseDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public byte[] getImage(Long genreId) throws ApplicationException {
        GenreEntity genreEntity = genreRepository.findById(genreId).
                orElseThrow(() -> new ResourceIsNotFoundException("The genre is not exists"));
        return genreEntity.getImage();
    }

    @Override
    @Transactional
    public void saveImage(Long genreId, MultipartFile image) throws ApplicationException {
        GenreEntity genreEntity = genreRepository.findById(genreId).
                orElseThrow(() -> new ResourceIsNotFoundException("The genre is not exists"));
        try {
            genreEntity.setImage(image.getBytes());
        } catch (IOException ex) {
            throw new ApplicationException("Error with file", ex);
        }

    }

    @Override
    @Transactional
    public List<GenreBriefResponseDto> searchGenres(String query) throws ApplicationException {
        return genreRepository.findAllByNameContainsIgnoreCase(query).
                stream().
                map(GenreBriefResponseDto::new).
                collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GenreFullResponseDto createGenre(String name, String description) throws ApplicationException {
        GenreEntity genre = new GenreEntity();
        genre.setName(name);
        genre.setDescription(description);
        genre = genreRepository.save(genre);
        return new GenreFullResponseDto(genre);
    }

    @Override
    @Transactional
    public GenreFullResponseDto changeGenre(String name, String description, Long genreId) throws ApplicationException {
        GenreEntity genreEntity = genreRepository.findById(genreId).
                orElseThrow(() -> new ResourceIsNotFoundException("The genre is not exists"));
        genreEntity.setDescription(description);
        genreEntity.setName(name);
        return new GenreFullResponseDto(genreEntity);
    }

    @Override
    @Transactional
    public void deleteGenre(Long genreId) throws ApplicationException {
        if (!genreRepository.existsById(genreId)) {
            throw new ResourceIsNotFoundException("The genre is not exists");
        }
        genreRepository.deleteById(genreId);
    }
}
