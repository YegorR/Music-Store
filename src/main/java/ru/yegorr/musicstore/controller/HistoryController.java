package ru.yegorr.musicstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.yegorr.musicstore.dto.response.ResponseBuilder;
import ru.yegorr.musicstore.dto.response.TrackSuperFullResponseDto;
import ru.yegorr.musicstore.security.UserChecker;
import ru.yegorr.musicstore.service.HistoryService;

import java.util.List;

@RestController
public class HistoryController {
    private final HistoryService historyService;

    private final UserChecker userChecker;

    @Autowired
    public HistoryController(HistoryService historyService, UserChecker userChecker) {
        this.historyService = historyService;
        this.userChecker = userChecker;
    }

    @GetMapping(path = "/history")
    public ResponseEntity<?> getHistory(@RequestHeader("Authorization") String token) throws Exception {
        Long userId = userChecker.getUserIdOrThrow(token);

        List<TrackSuperFullResponseDto> result = historyService.getHistory(userId);

        return ResponseBuilder.getBuilder().body(result).code(200).getResponseEntity();
    }
}
