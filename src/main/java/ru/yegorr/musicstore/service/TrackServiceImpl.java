package ru.yegorr.musicstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.response.TrackFullResponseDto;
import ru.yegorr.musicstore.dto.response.TrackResponseDto;
import ru.yegorr.musicstore.entity.TrackEntity;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.exception.ResourceIsNotFoundException;
import ru.yegorr.musicstore.repository.TrackRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;

    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    @Transactional
    public void saveAudio(MultipartFile audio, Long trackId) throws ApplicationException {
        TrackEntity track = trackRepository.findById(trackId).orElseThrow(() -> new ResourceIsNotFoundException("The track is not exist"));
        try {
            track.setAudio(audio.getBytes());
        } catch (IOException ex) {
            throw new ApplicationException("Error with audio file", ex);
        }
    }

    @Override
    @Transactional
    public byte[] getAudio(Long trackId) throws ApplicationException {
        TrackEntity track = trackRepository.findById(trackId).orElseThrow(() -> new ResourceIsNotFoundException("The track is not exist"));
        track.setPlaysNumber(track.getPlaysNumber() + 1);
        return track.getAudio();
    }

    @Override
    @Transactional
    public TrackResponseDto getTrackInfo(Long trackId) throws ApplicationException {
        TrackEntity track = trackRepository.findById(trackId).orElseThrow(() -> new ResourceIsNotFoundException("The track is not exist"));
        TrackResponseDto response = new TrackResponseDto();
        response.setId(trackId);
        response.setName(track.getName());
        response.setAlbumId(track.getAlbum().getAlbumId());
        response.setAlbumName(track.getAlbum().getName());
        response.setMusicianId(track.getAlbum().getMusician().getMusicianId());
        response.setMusicianName(track.getAlbum().getMusician().getName());

        return response;
    }

    @Override
    public List<TrackFullResponseDto> searchTracks(String query) throws ApplicationException {
        return trackRepository.findAllByNameContainingIgnoreCaseOrderByPlaysNumberDesc(query).stream().map((entity) -> {
            TrackFullResponseDto dto = new TrackFullResponseDto();
            dto.setId(entity.getTrackId());
            dto.setName(entity.getName());
            dto.setPlaysNumber(entity.getPlaysNumber());
            dto.setAlbumId(entity.getAlbum().getAlbumId());
            dto.setAlbumName(entity.getAlbum().getName());
            dto.setMusicianId(entity.getAlbum().getMusician().getMusicianId());
            dto.setMusicianName(entity.getAlbum().getMusician().getName());

            return dto;
        }).collect(Collectors.toList());
    }
}
