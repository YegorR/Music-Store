package ru.yegorr.musicstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.response.TrackFullResponseDto;
import ru.yegorr.musicstore.dto.response.TrackResponseDto;
import ru.yegorr.musicstore.exception.ApplicationException;

import java.util.List;

public interface TrackService {
    void saveAudio(MultipartFile audio, Long trackId) throws ApplicationException;

    byte[] getAudio(Long trackId, Long userId) throws ApplicationException;

    TrackResponseDto getTrackInfo(Long trackId) throws ApplicationException;

    List<TrackFullResponseDto> searchTracks(String query) throws ApplicationException;
}
