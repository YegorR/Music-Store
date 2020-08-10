package ru.yegorr.musicstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.response.TrackFullResponseDto;
import ru.yegorr.musicstore.dto.response.TrackResponseDto;
import ru.yegorr.musicstore.entity.HistoryEntity;
import ru.yegorr.musicstore.entity.TrackEntity;
import ru.yegorr.musicstore.exception.ClientException;
import ru.yegorr.musicstore.exception.ServerException;
import ru.yegorr.musicstore.repository.HistoryRepository;
import ru.yegorr.musicstore.repository.TrackRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;

    private final HistoryRepository historyRepository;

    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository, HistoryRepository historyRepository) {
        this.trackRepository = trackRepository;
        this.historyRepository = historyRepository;
    }

    @Override
    @Transactional
    public void saveAudio(MultipartFile audio, Long trackId) throws ServerException {
        TrackEntity track = trackRepository.findById(trackId).orElseThrow(() ->
                new ClientException(HttpStatus.NOT_FOUND, "The track is not exist"));
        try {
            track.setAudio(audio.getBytes());
        } catch (IOException ex) {
            throw new ServerException(ex);
        }
    }

    @Override
    @Transactional
    public byte[] getAudio(Long trackId, Long userId) throws ClientException {
        TrackEntity track = trackRepository.findById(trackId).orElseThrow(() ->
                new ClientException(HttpStatus.NOT_FOUND, "The track is not exist"));
        track.setPlaysNumber(track.getPlaysNumber() + 1);

        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setTrackId(trackId);
        historyEntity.setUserId(userId);
        historyEntity.setPlayTime(LocalDateTime.now());
        historyRepository.save(historyEntity);

        return track.getAudio();
    }

    @Override
    @Transactional
    public TrackResponseDto getTrackInfo(Long trackId) throws ClientException {
        TrackEntity track = trackRepository.findById(trackId).orElseThrow(() ->
                new ClientException(HttpStatus.NOT_FOUND, "The track is not exist"));
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
    public List<TrackFullResponseDto> searchTracks(String query) {
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
