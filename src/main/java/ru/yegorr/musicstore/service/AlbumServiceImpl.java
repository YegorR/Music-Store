package ru.yegorr.musicstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yegorr.musicstore.dto.request.ChangeAlbumRequestDto;
import ru.yegorr.musicstore.dto.request.CreateAlbumRequestDto;
import ru.yegorr.musicstore.dto.response.AlbumResponseDto;
import ru.yegorr.musicstore.dto.response.MusicianBriefResponseDto;
import ru.yegorr.musicstore.dto.response.TrackBriefResponseDto;
import ru.yegorr.musicstore.entity.AlbumEntity;
import ru.yegorr.musicstore.entity.MusicianEntity;
import ru.yegorr.musicstore.entity.TrackEntity;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.exception.ResourceIsNotFoundException;
import ru.yegorr.musicstore.repository.AlbumRepository;
import ru.yegorr.musicstore.repository.MusicianRepository;
import ru.yegorr.musicstore.repository.TrackRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumServiceImpl implements AlbumService {
    private MusicianRepository musicianRepository;

    private TrackRepository trackRepository;

    private AlbumRepository albumRepository;

    @Override
    @Transactional
    public AlbumResponseDto createAlbum(CreateAlbumRequestDto createAlbumRequest, Long musicianId) throws ApplicationException {
        if (!musicianRepository.existsById(musicianId)) {
            throw new ResourceIsNotFoundException("The musician is not exists");
        }

        AlbumEntity album = new AlbumEntity();
        album.setName(createAlbumRequest.getName());
        album.setReleaseDate(createAlbumRequest.getReleaseDate());
        MusicianEntity musician = new MusicianEntity();
        musician.setMusicianId(musicianId);
        album.setMusician(musician);
        album.setSingle(createAlbumRequest.isSingle());

        album = albumRepository.save(album);

        List<TrackEntity> tracks = new ArrayList<>();
        int order = 0;
        for (CreateAlbumRequestDto.Track track : createAlbumRequest.getTracks()) {
            TrackEntity trackEntity = new TrackEntity();
            trackEntity.setName(track.getName());
            trackEntity.setAlbum(album);
            trackEntity.setOrder(order++);
            trackEntity.setPlaysNumber(0);
            tracks.add(trackEntity);
            trackRepository.save(trackEntity);
        }
        album.setTracks(tracks);

        album = albumRepository.save(album);
        return translateEntityToDto(album);
    }

    @Override
    @Transactional
    public AlbumResponseDto changeAlbum(ChangeAlbumRequestDto changeAlbumRequest, Long albumId) throws ApplicationException {
        Optional<AlbumEntity> albumOptional = albumRepository.findById(albumId);
        if (albumOptional.isEmpty()) {
            throw new ResourceIsNotFoundException("The album is not exists");
        }
        AlbumEntity album = albumOptional.get();

        album.setName(changeAlbumRequest.getName());
        album.setReleaseDate(changeAlbumRequest.getReleaseDate());
        album.setSingle(changeAlbumRequest.isSingle());

        List<TrackEntity> oldTracks = album.getTracks();
        List<TrackEntity> newTracks = new ArrayList<>();

        int order = 0;
        for (ChangeAlbumRequestDto.Track track : changeAlbumRequest.getTracks()) {
            Long id = track.getId();
            TrackEntity trackEntity;
            if (id == null) {
                trackEntity = new TrackEntity();
                trackEntity.setPlaysNumber(0);
            } else {
                Optional<TrackEntity> trackEntityOptional = trackRepository.findById(id);
                if (trackEntityOptional.isEmpty()) {
                    throw new ResourceIsNotFoundException(String.format("The track %d is not exist", id));
                }
                trackEntity = trackEntityOptional.get();
                oldTracks.removeIf(oldTrack -> oldTrack.getTrackId().equals(id));
            }
            trackEntity.setName(track.getName());
            trackEntity.setAlbum(album);
            trackEntity.setOrder(order++);
            newTracks.add(trackEntity);
        }
        trackRepository.deleteAll(oldTracks);
        trackRepository.saveAll(newTracks);
        album.setTracks(newTracks);
        album = albumRepository.save(album);

        return translateEntityToDto(album);
    }

    @Override
    @Transactional
    public AlbumResponseDto getAlbum(Long albumId) throws ApplicationException {
        AlbumEntity album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ResourceIsNotFoundException("The album is not exist"));
        return translateEntityToDto(album);
    }

    @Override
    @Transactional
    public void deleteAlbum(Long albumId) throws ApplicationException {
        if (!albumRepository.existsById(albumId)) {
            throw new ResourceIsNotFoundException("The album is not exist");
        }
        albumRepository.deleteById(albumId);
    }

    private AlbumResponseDto translateEntityToDto(AlbumEntity entity) { //TODO Add version for favourite
        AlbumResponseDto dto = new AlbumResponseDto();
        dto.setId(entity.getAlbumId());
        dto.setName(entity.getName());
        dto.setReleaseDate(entity.getReleaseDate());
        dto.setSingle(entity.getSingle());

        MusicianBriefResponseDto musician = new MusicianBriefResponseDto();
        musician.setId(entity.getMusician().getMusicianId());
        musician.setName(entity.getMusician().getName());
        dto.setMusician(musician);

        List<TrackBriefResponseDto> tracks = new ArrayList<>();
        for (TrackEntity trackEntity : entity.getTracks()) {
            TrackBriefResponseDto track = new TrackBriefResponseDto();
            track.setId(trackEntity.getTrackId());
            track.setFavourite(false);
            track.setPlaysNumber(trackEntity.getPlaysNumber());
            track.setName(trackEntity.getName());
            tracks.add(track);
        }
        dto.setTracks(tracks);

        return dto;
    }

    @Autowired
    public void setMusicianRepository(MusicianRepository musicianRepository) {
        this.musicianRepository = musicianRepository;
    }

    @Autowired
    public void setTrackRepository(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Autowired
    public void setAlbumRepository(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }
}
