package ru.yegorr.musicstore.service;

import ru.yegorr.musicstore.dto.request.SubscriptionStatusDto;
import ru.yegorr.musicstore.dto.response.MusicianResponseDto;
import ru.yegorr.musicstore.dto.response.SubscriptionCountDto;
import ru.yegorr.musicstore.exception.ApplicationException;

import java.util.List;

public interface SubscriptionService {
    SubscriptionStatusDto getSubscriptionStatus(Long userId, Long musicianId) throws ApplicationException;

    SubscriptionStatusDto changeSubscriptionStatus(Long userId, Long musicianId, SubscriptionStatusDto newStatus) throws ApplicationException;

    SubscriptionCountDto getReleasesNumber(Long userId) throws ApplicationException;

    List<MusicianResponseDto> getReleases(Long userId) throws ApplicationException;
}
