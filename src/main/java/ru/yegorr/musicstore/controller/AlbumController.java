package ru.yegorr.musicstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yegorr.musicstore.dto.request.ChangeAlbumRequestDto;
import ru.yegorr.musicstore.dto.request.CreateAlbumRequestDto;
import ru.yegorr.musicstore.dto.response.AlbumResponseDto;
import ru.yegorr.musicstore.dto.response.ResponseBuilder;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.exception.ForbiddenException;
import ru.yegorr.musicstore.service.AlbumService;

@RestController
public class AlbumController {

    private final UserChecker userChecker;

    private final AlbumService albumService;

    @Autowired
    public AlbumController(UserChecker userChecker, AlbumService albumService) {
        this.userChecker = userChecker;
        this.albumService = albumService;
    }

    @PostMapping("/musician/{musicianId}/album")
    public ResponseEntity<?> createAlbum(@RequestBody CreateAlbumRequestDto request,
                                         @PathVariable("musicianId") Long musicianId,
                                         @RequestHeader("Authorization") String token) throws ApplicationException {
        if (!userChecker.isAdmin(token)) {
            throw new ForbiddenException("You have not rights for this");
        }

        AlbumResponseDto response = albumService.createAlbum(request, musicianId);
        return ResponseBuilder.getBuilder().body(response).code(201).getResponseEntity();
    }

    @PutMapping("/album/{albumId}")
    public ResponseEntity<?> changeAlbum(@RequestBody ChangeAlbumRequestDto request,
                                         @PathVariable("albumId") Long albumId,
                                         @RequestHeader("Authorization") String token) throws ApplicationException {
        if (!userChecker.isAdmin(token)) {
            throw new ForbiddenException("You have not rights for this");
        }

        AlbumResponseDto response = albumService.changeAlbum(request, albumId);
        return ResponseBuilder.getBuilder().body(response).code(200).getResponseEntity();
    }

    @GetMapping("/album/{albumId}")
    public ResponseEntity<?> getAlbum(@PathVariable("albumId") Long albumId,
                                      @RequestHeader("Authorization") String token) throws ApplicationException {
        userChecker.getUserId(token);
        AlbumResponseDto response = albumService.getAlbum(albumId);
        return ResponseBuilder.getBuilder().body(response).code(200).getResponseEntity();
    }

    @DeleteMapping("album/{albumId}")
    public ResponseEntity<?> deleteAlbum(@PathVariable("albumId") Long albumId,
                                         @RequestHeader("Authorization") String token) throws ApplicationException {
        if (!userChecker.isAdmin(token)) {
            throw new ForbiddenException("You have not rights for this");
        }
        albumService.deleteAlbum(albumId);
        return ResponseBuilder.getBuilder().code(200).getResponseEntity();
    }
}
