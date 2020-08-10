package ru.yegorr.musicstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.response.TrackFullResponseDto;
import ru.yegorr.musicstore.dto.response.TrackResponseDto;
import ru.yegorr.musicstore.exception.ClientException;
import ru.yegorr.musicstore.exception.ServerException;

import java.util.List;

public interface TrackService {
    void saveAudio(MultipartFile audio, Long trackId) throws ServerException;

    byte[] getAudio(Long trackId, Long userId) throws ClientException;

    TrackResponseDto getTrackInfo(Long trackId) throws ClientException;

    List<TrackFullResponseDto> searchTracks(String query) throws ClientException;
}
