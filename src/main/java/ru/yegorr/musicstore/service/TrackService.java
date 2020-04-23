package ru.yegorr.musicstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.exception.ApplicationException;

public interface TrackService {
    void saveAudio(MultipartFile audio, Long trackId) throws ApplicationException;

    byte[] getAudio(Long trackId) throws ApplicationException;
}
