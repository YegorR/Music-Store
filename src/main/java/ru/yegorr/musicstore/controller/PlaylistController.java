package ru.yegorr.musicstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.request.ChangePlaylistRequest;
import ru.yegorr.musicstore.dto.request.CreatePlaylistRequest;
import ru.yegorr.musicstore.dto.response.PlaylistBriefDto;
import ru.yegorr.musicstore.dto.response.PlaylistResponseDto;
import ru.yegorr.musicstore.dto.response.ResponseBuilder;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.service.PlaylistService;

import java.util.Base64;
import java.util.List;

@RestController
public class PlaylistController {
    private final PlaylistService playlistService;

    private final UserChecker userChecker;

    @Autowired
    public PlaylistController(PlaylistService playlistService, UserChecker userChecker) {
        this.playlistService = playlistService;
        this.userChecker = userChecker;
    }

    @GetMapping(path = "/playlist/{playlistId}")
    public ResponseEntity<?> getPlaylist(@PathVariable Long playlistId,
                                         @RequestHeader("Authorization") String token) throws ApplicationException {
        Long userId = userChecker.getUserIdOrThrow(token);

        PlaylistResponseDto response = playlistService.getPlaylist(userId, playlistId);

        return ResponseBuilder.getBuilder().body(response).code(200).getResponseEntity();
    }

    @PostMapping(path = "/playlist")
    public ResponseEntity<?> createPlaylist(@RequestBody CreatePlaylistRequest request,
                                            @RequestHeader("Authorization") String token) throws ApplicationException {
        Long userId = userChecker.getUserIdOrThrow(token);

        PlaylistResponseDto response = playlistService.createPlaylist(userId, request.getName());

        return ResponseBuilder.getBuilder().body(response).code(200).getResponseEntity();
    }

    @PutMapping(path = "/playlist/{playlistId}")
    public ResponseEntity<?> changePlaylist(@RequestBody ChangePlaylistRequest request, @PathVariable Long playlistId,
                                            @RequestHeader("Authorization") String token) throws ApplicationException {
        Long userId = userChecker.getUserIdOrThrow(token);

        PlaylistResponseDto response = playlistService.changePlaylist(userId, playlistId,
                request.getName(), request.getTracks());

        return ResponseBuilder.getBuilder().body(response).code(200).getResponseEntity();
    }

    @DeleteMapping(path = "/playlist/{playlistId}")
    public ResponseEntity<?> deletePlaylist(@PathVariable Long playlistId,
                                            @RequestHeader("Authorization") String token) throws ApplicationException {
        Long userId = userChecker.getUserIdOrThrow(token);

        playlistService.deletePlaylist(userId, playlistId);

        return ResponseBuilder.getBuilder().code(200).getResponseEntity();
    }

    @PutMapping(path = "/playlist/{playlistId}/image", consumes = "multipart/form-data")
    public ResponseEntity<?> loadImage(@PathVariable Long playlistId, @RequestHeader("Authorization") String token,
                                       @RequestParam("image") MultipartFile image) throws ApplicationException {
        Long userId = userChecker.getUserIdOrThrow(token);

        playlistService.unloadPlaylistImage(userId, playlistId, image);
        return ResponseBuilder.getBuilder().code(200).getResponseEntity();
    }

    @GetMapping(path = "/playlist/{playlistId}/image", produces = "text/plain")
    public ResponseEntity<?> getImage(@PathVariable Long playlistId,
                                      @RequestHeader("Authorization") String token) throws ApplicationException {
        userChecker.getUserIdOrThrow(token);

        byte[] image = playlistService.getPlaylistImage(playlistId);
        if (image == null) {
            image = new byte[0];
        }
        return ResponseEntity.ok(Base64.getEncoder().encodeToString(image));
    }

    @GetMapping(path = "/user/{userId}/playlists")
    public ResponseEntity<?> getPlaylistsByUser(@PathVariable Long userId,
                                                @RequestHeader("Authorization") String token)
            throws ApplicationException {
        userChecker.getUserIdOrThrow(token);

        List<PlaylistBriefDto> response = playlistService.getPlaylistsOfUser(userId);

        return ResponseBuilder.getBuilder().body(response).code(200).getResponseEntity();
    }

    @GetMapping(path = "/playlists")
    public ResponseEntity<?> searchPlaylists(@RequestParam String query,
                                             @RequestHeader("Authorization") String token)
            throws ApplicationException {
        userChecker.getUserIdOrThrow(token);

        List<PlaylistBriefDto> response = playlistService.searchPlaylists(query);

        return ResponseBuilder.getBuilder().body(response).code(200).getResponseEntity();
    }
}
