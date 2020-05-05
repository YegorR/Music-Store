package ru.yegorr.musicstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yegorr.musicstore.dto.response.AlbumDescriptionDto;
import ru.yegorr.musicstore.dto.response.MusicianResponseDto;
import ru.yegorr.musicstore.entity.AlbumEntity;
import ru.yegorr.musicstore.entity.MusicianEntity;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.exception.ResourceIsNotFoundException;
import ru.yegorr.musicstore.repository.MusicianAbcCount;
import ru.yegorr.musicstore.repository.MusicianRepository;

import java.util.*;

@Service
public class MusicianServiceImpl implements MusicianService {

    private final MusicianRepository musicianRepository;

    @Autowired
    public MusicianServiceImpl(MusicianRepository musicianRepository) {
        this.musicianRepository = musicianRepository;
    }

    @Transactional
    @Override
    public MusicianResponseDto createMusician(String name, String description) throws ApplicationException {
        MusicianEntity musician = new MusicianEntity();
        musician.setName(name);
        musician.setDescription(description);
        musician = musicianRepository.save(musician);

        return translateEntityToDto(musician);
    }

    @Transactional
    @Override
    public MusicianResponseDto changeMusician(Long id, String name, String description) throws ApplicationException {
        if (!musicianRepository.existsById(id)) {
            throw new ResourceIsNotFoundException("The musician is not exists");
        }

        MusicianEntity musician = new MusicianEntity();
        musician.setName(name);
        musician.setDescription(description);
        musician.setMusicianId(id);
        musician = musicianRepository.save(musician);

        return translateEntityToDto(musician);
    }

    @Transactional
    @Override
    public void deleteMusician(Long id) throws ApplicationException {
        if (!musicianRepository.existsById(id)) {
            throw new ResourceIsNotFoundException("The musician is not exists");
        }
        musicianRepository.deleteById(id);
    }

    @Transactional
    @Override
    public MusicianResponseDto getMusician(Long id) throws ApplicationException {
        Optional<MusicianEntity> musician = musicianRepository.findById(id);
        if (musician.isEmpty()) {
            throw new ResourceIsNotFoundException("The musician is not exists");
        }
        return translateEntityToDto(musician.get());
    }

    @Override
    public Map<String, Integer> getMusicianCountByAbc() throws ApplicationException {
        List<MusicianAbcCount> counts = musicianRepository.getMusicianCountByAbc();
        Map<String, Integer> result = new LinkedHashMap<>();
        for (MusicianAbcCount count: counts) {
            result.put(count.getLetter(), count.getCount());
        }
        return result;
    }

    private MusicianResponseDto translateEntityToDto(MusicianEntity entity) {
        MusicianResponseDto musicianResponseDto = new MusicianResponseDto();
        musicianResponseDto.setId(entity.getMusicianId());
        musicianResponseDto.setDescription(entity.getDescription());
        musicianResponseDto.setName(entity.getName());

        List<AlbumEntity> albumsAndSingles = entity.getAlbums();

        if (albumsAndSingles == null) {
            return musicianResponseDto;
        }
        
        List<AlbumDescriptionDto> albums = new ArrayList<>();
        List<AlbumDescriptionDto> singles = new ArrayList<>();

        for (AlbumEntity release: albumsAndSingles) {
            AlbumDescriptionDto releaseDto = new AlbumDescriptionDto();
            releaseDto.setId(release.getAlbumId());
            releaseDto.setName(release.getName());
            releaseDto.setReleaseDate(release.getReleaseDate());
            releaseDto.setSingle(release.getSingle());

            if (release.getSingle()) {
                singles.add(releaseDto);
            } else {
                albums.add(releaseDto);
            }
        }

        musicianResponseDto.setSingles(singles);
        musicianResponseDto.setAlbums(albums);

        return musicianResponseDto;
    }
}
