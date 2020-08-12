package ru.yegorr.musicstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yegorr.musicstore.dto.request.SetFavouriteDto;
import ru.yegorr.musicstore.dto.response.TrackWithPlaysNumberDto;
import ru.yegorr.musicstore.response.ResponseBuilder;
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
  public ResponseEntity<?> setFavourite(@RequestBody SetFavouriteDto favouriteRequest,
                                        @RequestHeader("Authorization") String token)
          throws Exception {
    Long userId = userChecker.getUserIdOrThrow(token);

    favouriteService.setFavourite(userId, favouriteRequest.getId(), favouriteRequest.isFavourite());

    return ResponseBuilder.getBuilder().code(HttpStatus.OK).body(favouriteRequest).
            getResponseEntity();
  }

  @GetMapping(path = "/favourite")
  public ResponseEntity<?> getFavourite(@RequestHeader("Authorization") String token)
          throws Exception {
    Long userId = userChecker.getUserIdOrThrow(token);

    List<TrackWithPlaysNumberDto> response = favouriteService.getFavouriteList(userId);
    return ResponseBuilder.getBuilder().code(HttpStatus.OK).body(response).getResponseEntity();
  }
}
