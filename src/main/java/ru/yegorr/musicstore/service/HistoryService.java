package ru.yegorr.musicstore.service;

import ru.yegorr.musicstore.dto.response.FullTrackDto;

import java.util.List;

public interface HistoryService {
  List<FullTrackDto> getHistory(Long userId);
}
