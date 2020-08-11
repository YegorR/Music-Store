package ru.yegorr.musicstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.request.ChangeAlbumDto;
import ru.yegorr.musicstore.dto.request.CreateAlbumDto;
import ru.yegorr.musicstore.dto.response.FullAlbumDto;
import ru.yegorr.musicstore.dto.response.BriefAlbumDescriptionDto;
import ru.yegorr.musicstore.response.ResponseBuilder;
import ru.yegorr.musicstore.exception.ClientException;
import ru.yegorr.musicstore.security.UserChecker;
import ru.yegorr.musicstore.service.AlbumService;

import java.util.Base64;
import java.util.List;

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
    public ResponseEntity<?> createAlbum(@RequestBody CreateAlbumDto request,
                                         @PathVariable("musicianId") Long musicianId,
                                         @RequestHeader("Authorization") String token) throws Exception {
        userChecker.checkAdmin(token);

        FullAlbumDto response = albumService.createAlbum(request, musicianId);
        return ResponseBuilder.getBuilder().body(response).code(HttpStatus.CREATED).getResponseEntity();
    }

    @PutMapping("/album/{albumId}")
    public ResponseEntity<?> changeAlbum(@RequestBody ChangeAlbumDto request,
                                         @PathVariable("albumId") Long albumId,
                                         @RequestHeader("Authorization") String token) throws Exception {
        userChecker.checkAdmin(token);

        FullAlbumDto response = albumService.changeAlbum(request, albumId);
        return ResponseBuilder.getBuilder().body(response).code(HttpStatus.OK).getResponseEntity();
    }

    @GetMapping("/album/{albumId}")
    public ResponseEntity<?> getAlbum(@PathVariable("albumId") Long albumId,
                                      @RequestHeader("Authorization") String token) throws Exception {
        Long userId = userChecker.getUserIdOrThrow(token);
        FullAlbumDto response = albumService.getAlbum(albumId, userId);
        return ResponseBuilder.getBuilder().body(response).code(HttpStatus.OK).getResponseEntity();
    }

    @DeleteMapping("album/{albumId}")
    public ResponseEntity<?> deleteAlbum(@PathVariable("albumId") Long albumId,
                                         @RequestHeader("Authorization") String token) throws Exception {
        userChecker.checkAdmin(token);
        albumService.deleteAlbum(albumId);
        return ResponseBuilder.getBuilder().code(HttpStatus.NO_CONTENT).getResponseEntity();
    }

    @PutMapping(path = "/album/{albumId}/image",
            consumes = "multipart/form-data")
    public ResponseEntity<?> unloadCover(@PathVariable("albumId") Long albumId,
                                         @RequestHeader("Authorization") String token,
                                         @RequestParam("image") MultipartFile cover) throws Exception {
        userChecker.checkAdmin(token);
        albumService.saveCover(albumId, cover);
        return ResponseBuilder.getBuilder().code(HttpStatus.OK).getResponseEntity();
    }

    @GetMapping(path = "album/{albumId}/image",
        produces = "text/plain")
    public ResponseEntity<?> getCover(@PathVariable("albumId") Long albumId,
                                      @RequestHeader("Authorization") String token) throws Exception {
        userChecker.getUserIdOrThrow(token);

        byte[] cover = albumService.getCover(albumId);
        if (cover == null) {
            cover = new byte[0];
        }
        return ResponseEntity.ok(Base64.getEncoder().encodeToString(cover));
    }

    @GetMapping(path = "albums")
    public ResponseEntity<?> search(@RequestParam("query") String query,
                                    @RequestHeader("Authorization") String token) throws Exception {
        userChecker.getUserIdOrThrow(token);

        query = query.strip();
        if (query.isEmpty()) {
            throw new ClientException("No query");
        }
        List<BriefAlbumDescriptionDto> response = albumService.searchAlbums(query);
        return ResponseBuilder.getBuilder().code(HttpStatus.OK).body(response).getResponseEntity();
    }
}
