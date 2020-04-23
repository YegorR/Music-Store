package ru.yegorr.musicstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.entity.TrackEntity;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.exception.ResourceIsNotFoundException;
import ru.yegorr.musicstore.repository.TrackRepository;

import java.io.IOException;

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
        return track.getAudio();
    }
}
