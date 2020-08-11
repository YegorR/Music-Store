package ru.yegorr.musicstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.response.TrackWithPlaysNumberDto;
import ru.yegorr.musicstore.dto.response.TrackDto;
import ru.yegorr.musicstore.exception.ClientException;
import ru.yegorr.musicstore.exception.ServerException;

import java.util.List;

public interface TrackService {
    void saveAudio(MultipartFile audio, Long trackId) throws ServerException;

    byte[] getAudio(Long trackId, Long userId) throws ClientException;

    TrackDto getTrackInfo(Long trackId) throws ClientException;

    List<TrackWithPlaysNumberDto> searchTracks(String query) throws ClientException;
}
