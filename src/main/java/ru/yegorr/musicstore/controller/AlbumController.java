package ru.yegorr.musicstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yegorr.musicstore.dto.request.CreateAlbumRequestDto;
import ru.yegorr.musicstore.dto.response.AlbumResponseDto;
import ru.yegorr.musicstore.dto.response.ResponseBuilder;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.exception.ForbiddenException;
import ru.yegorr.musicstore.service.AlbumService;

@RestController
public class AlbumController {

    private final UserChecker userChecker;

    private final AlbumService albumRepository;

    @Autowired
    public AlbumController(UserChecker userChecker, AlbumService albumRepository) {
        this.userChecker = userChecker;
        this.albumRepository = albumRepository;
    }

    @PostMapping("/musician/{musicianId}/album")
    public ResponseEntity<?> createAlbum(@RequestBody CreateAlbumRequestDto request,
                                         @PathVariable("musicianId") Long musicianId,
                                         @RequestHeader("Authorization") String token) throws ApplicationException {
        if (!userChecker.isAdmin(token)) {
            throw new ForbiddenException("You have not rights for this");
        }

        AlbumResponseDto response = albumRepository.createAlbum(request, musicianId);
        return ResponseBuilder.getBuilder().body(response).code(201).getResponseEntity();
    }
}
