package ru.yegorr.musicstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.request.ChangeAlbumRequestDto;
import ru.yegorr.musicstore.dto.request.CreateAlbumRequestDto;
import ru.yegorr.musicstore.dto.response.AlbumResponseDto;
import ru.yegorr.musicstore.dto.response.BriefAlbumDescriptionDto;
import ru.yegorr.musicstore.dto.response.MusicianBriefResponseDto;
import ru.yegorr.musicstore.dto.response.TrackBriefResponseDto;
import ru.yegorr.musicstore.entity.AlbumEntity;
import ru.yegorr.musicstore.entity.FavouriteEntity;
import ru.yegorr.musicstore.entity.MusicianEntity;
import ru.yegorr.musicstore.entity.TrackEntity;
import ru.yegorr.musicstore.exception.ClientException;
import ru.yegorr.musicstore.exception.ServerException;
import ru.yegorr.musicstore.repository.AlbumRepository;
import ru.yegorr.musicstore.repository.FavouriteRepository;
import ru.yegorr.musicstore.repository.MusicianRepository;
import ru.yegorr.musicstore.repository.TrackRepository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService {
    private MusicianRepository musicianRepository;

    private TrackRepository trackRepository;

    private AlbumRepository albumRepository;

    private FavouriteRepository favouriteRepository;

    @Override
    @Transactional
    public AlbumResponseDto createAlbum(CreateAlbumRequestDto createAlbumRequest, Long musicianId) throws ClientException {
        if (!musicianRepository.existsById(musicianId)) {
            throw new ClientException(HttpStatus.NOT_FOUND, "The musician is not exists");
        }

        AlbumEntity album = new AlbumEntity();
        album.setName(createAlbumRequest.getName());
        album.setReleaseDate(createAlbumRequest.getReleaseDate());
        MusicianEntity musician = musicianRepository.findById(musicianId).
                orElseThrow(() -> new ClientException(HttpStatus.NOT_FOUND, "The musician is not exists"));
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

        AlbumEntity finalAlbum = album;
        musician.getSubscribers().forEach((user) -> user.getReleaseAlbums().add(finalAlbum));
        return translateEntityToDto(album);
    }

    @Override
    @Transactional
    public AlbumResponseDto changeAlbum(ChangeAlbumRequestDto changeAlbumRequest, Long albumId) throws ClientException {
        Optional<AlbumEntity> albumOptional = albumRepository.findById(albumId);
        if (albumOptional.isEmpty()) {
            throw new ClientException(HttpStatus.NOT_FOUND, "The album is not exists");
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
                    throw new ClientException(HttpStatus.NOT_FOUND, String.format("The track %d is not exist", id));
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
    public AlbumResponseDto getAlbum(Long albumId, Long userId) throws ClientException {
        AlbumEntity album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ClientException(HttpStatus.NOT_FOUND, "The album is not exist"));
        return translateEntityToDto(album, userId);
    }

    @Override
    @Transactional
    public void deleteAlbum(Long albumId) throws ClientException {
        if (!albumRepository.existsById(albumId)) {
            throw new ClientException(HttpStatus.NOT_FOUND, "The album is not exist");
        }
        albumRepository.deleteById(albumId);
    }

    @Override
    @Transactional
    public byte[] getCover(Long albumId) throws ClientException {
        return albumRepository.findById(albumId).
                orElseThrow(() -> new ClientException(HttpStatus.NOT_FOUND, "The album is not exists")).
                getCover();
    }

    @Override
    @Transactional
    public void saveCover(Long albumId, MultipartFile cover) throws ServerException {
        try {
            albumRepository.findById(albumId).
                    orElseThrow(() -> new ClientException(HttpStatus.NOT_FOUND, "The album is not exists")).
                    setCover(cover.getBytes());
        } catch (IOException ex) {
            throw new ServerException(ex);
        }
    }

    @Override
    public List<BriefAlbumDescriptionDto> searchAlbums(String query) {
        List<AlbumEntity> albums = albumRepository.findAllByNameContainingIgnoreCase(query);
        return albums.stream().map(this::translateEntityToBriefDto).collect(Collectors.toList());
    }

    private BriefAlbumDescriptionDto translateEntityToBriefDto(AlbumEntity entity) {
        BriefAlbumDescriptionDto dto = new BriefAlbumDescriptionDto();
        dto.setId(entity.getAlbumId());
        dto.setName(entity.getName());
        dto.setReleaseDate(entity.getReleaseDate());
        dto.setSingle(entity.getSingle());

        MusicianBriefResponseDto musician = new MusicianBriefResponseDto();
        musician.setId(entity.getMusician().getMusicianId());
        musician.setName(entity.getMusician().getName());
        dto.setMusician(musician);

        return dto;
    }

    private AlbumResponseDto translateEntityToDto(AlbumEntity entity, Long userId) {
        AlbumResponseDto dto = new AlbumResponseDto();
        dto.setId(entity.getAlbumId());
        dto.setName(entity.getName());
        dto.setReleaseDate(entity.getReleaseDate());
        dto.setSingle(entity.getSingle());

        MusicianBriefResponseDto musician = new MusicianBriefResponseDto();
        musician.setId(entity.getMusician().getMusicianId());
        musician.setName(entity.getMusician().getName());
        dto.setMusician(musician);

        Set<TrackEntity> favourites;
        if (userId != null) {
            favourites = favouriteRepository.findAllByUserIdAndTrackIn(userId, entity.getTracks()).
                    stream().
                    map(FavouriteEntity::getTrack).
                    collect(Collectors.toSet());
        } else {
            favourites = new HashSet<>();
        }
        List<TrackBriefResponseDto> tracks = new ArrayList<>();
        for (TrackEntity trackEntity : entity.getTracks()) {
            TrackBriefResponseDto track = new TrackBriefResponseDto();
            track.setId(trackEntity.getTrackId());
            track.setPlaysNumber(trackEntity.getPlaysNumber());
            track.setName(trackEntity.getName());
            track.setFavourite(favourites.contains(trackEntity));
            tracks.add(track);
        }
        dto.setTracks(tracks);

        return dto;
    }

    private AlbumResponseDto translateEntityToDto(AlbumEntity entity) { //TODO Add version for favourite
        return translateEntityToDto(entity, null);
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

    @Autowired
    public void setFavouriteRepository(FavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }
}
