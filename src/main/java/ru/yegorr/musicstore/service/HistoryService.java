package ru.yegorr.musicstore.service;

import ru.yegorr.musicstore.dto.response.TrackSuperFullResponseDto;

import java.util.List;

public interface HistoryService {
    List<TrackSuperFullResponseDto> getHistory(Long userId);
}
