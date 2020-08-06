package ru.yegorr.musicstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yegorr.musicstore.dto.request.SubscriptionStatusDto;
import ru.yegorr.musicstore.dto.response.AlbumDescriptionDto;
import ru.yegorr.musicstore.dto.response.MusicianResponseDto;
import ru.yegorr.musicstore.dto.response.SubscriptionCountDto;
import ru.yegorr.musicstore.entity.AlbumEntity;
import ru.yegorr.musicstore.entity.MusicianEntity;
import ru.yegorr.musicstore.entity.UserEntity;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.exception.ResourceIsNotFoundException;
import ru.yegorr.musicstore.repository.MusicianRepository;
import ru.yegorr.musicstore.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final MusicianRepository musicianRepository;

    private final UserRepository userRepository;

    @Autowired
    public SubscriptionServiceImpl(MusicianRepository musicianRepository, UserRepository userRepository) {
        this.musicianRepository = musicianRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public SubscriptionStatusDto getSubscriptionStatus(Long userId, Long musicianId) throws ApplicationException {
        MusicianEntity musician = musicianRepository.findById(musicianId).
                orElseThrow(() -> new ResourceIsNotFoundException("The musician is not exists"));

        UserEntity userEntity = userRepository.findById(userId).get();

        SubscriptionStatusDto result = new SubscriptionStatusDto();
        result.setSubscription(musician.getSubscribers().contains(userEntity));

        return result;
    }

    @Transactional
    @Override
    public SubscriptionStatusDto changeSubscriptionStatus(Long userId, Long musicianId, SubscriptionStatusDto newStatus) throws ApplicationException {
        MusicianEntity musician = musicianRepository.findById(musicianId).
                orElseThrow(() -> new ResourceIsNotFoundException("The musician is not exists"));

        UserEntity userEntity = userRepository.findById(userId).get();

        if (!newStatus.isSubscription()) {
            musician.getSubscribers().remove(userEntity);
            userEntity.getSubscriptions().remove(musician);
            userEntity.setReleaseAlbums(userEntity.
                    getReleaseAlbums().
                    stream().
                    filter(albumEntity -> !albumEntity.
                            getMusician().
                            getMusicianId().
                            equals(musicianId)).
                    collect(Collectors.toList()));
        } else {
            if (!musician.getSubscribers().contains(userEntity)) {
                musician.getSubscribers().add(userEntity);
                userEntity.getSubscriptions().add(musician);
            }
        }

        return newStatus;
    }

    @Override
    @Transactional
    public SubscriptionCountDto getReleasesNumber(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).get();
        int releasesCount = userEntity.getReleaseAlbums().size();
        SubscriptionCountDto result = new SubscriptionCountDto();
        result.setCount(releasesCount);

        return result;
    }

    @Override
    @Transactional
    public List<MusicianResponseDto> getReleases(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).get();

        List<AlbumEntity> releaseAlbums = userEntity.getReleaseAlbums();

        Map<Long, MusicianResponseDto> result = new HashMap<>();
        releaseAlbums.forEach((entity) -> {
            MusicianEntity musicianEntity = entity.getMusician();

            MusicianResponseDto musicianDto;
            if (result.containsKey(musicianEntity.getMusicianId())) {
                musicianDto = result.get(musicianEntity.getMusicianId());
            } else {
                musicianDto = new MusicianResponseDto();
                musicianDto.setAlbums(new ArrayList<>());
                result.put(musicianEntity.getMusicianId(), musicianDto);
            }

            musicianDto.setId(musicianEntity.getMusicianId());
            musicianDto.setDescription(musicianEntity.getDescription());
            musicianDto.setName(musicianEntity.getName());

            AlbumDescriptionDto albumDto = new AlbumDescriptionDto();
            albumDto.setId(entity.getAlbumId());
            albumDto.setName(entity.getName());
            albumDto.setReleaseDate(entity.getReleaseDate());
            albumDto.setSingle(entity.getSingle());
            musicianDto.getAlbums().add(albumDto);
        });
        userEntity.getSubscriptions().forEach((musician) -> {
            if (!result.containsKey(musician.getMusicianId())) {
                MusicianResponseDto musicianDto = new MusicianResponseDto();
                musicianDto.setAlbums(new ArrayList<>());
                musicianDto.setName(musician.getName());
                musicianDto.setDescription(musician.getDescription());
                musicianDto.setId(musician.getMusicianId());
                result.put(musician.getMusicianId(), musicianDto);
            }
        });
        releaseAlbums.clear();
        return new ArrayList<>(result.values());
    }
}
