package ru.yegorr.musicstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yegorr.musicstore.dto.request.SetFavouriteRequest;
import ru.yegorr.musicstore.dto.response.ResponseBuilder;
import ru.yegorr.musicstore.dto.response.TrackFullResponseDto;
import ru.yegorr.musicstore.security.UserChecker;
import ru.yegorr.musicstore.service.FavouriteService;

import java.util.List;

@RestController
public class FavouriteController {

    private final FavouriteService favouriteService;

    private final UserChecker userChecker;

    @Autowired
    public FavouriteController(FavouriteService favouriteService, UserChecker userChecker) {
        this.favouriteService = favouriteService;
        this.userChecker = userChecker;
    }

    @PutMapping(path = "/favourite")
    public ResponseEntity<?> setFavourite(@RequestBody SetFavouriteRequest favouriteRequest,
                                          @RequestHeader("Authorization") String token) throws Exception {
        Long userId = userChecker.getUserIdOrThrow(token);

        favouriteService.setFavourite(userId, favouriteRequest.getId(), favouriteRequest.isFavourite());

        return ResponseBuilder.getBuilder().code(200).body(favouriteRequest).getResponseEntity();
    }

    @GetMapping(path = "/favourite")
    public ResponseEntity<?> getFavourite(@RequestHeader("Authorization") String token) throws Exception {
        Long userId = userChecker.getUserIdOrThrow(token);

        List<TrackFullResponseDto> response = favouriteService.getFavouriteList(userId);
        return ResponseBuilder.getBuilder().code(200).body(response).getResponseEntity();
    }
}
