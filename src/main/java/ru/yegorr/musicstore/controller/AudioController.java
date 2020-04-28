package ru.yegorr.musicstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.response.ResponseBuilder;
import ru.yegorr.musicstore.dto.response.TrackResponseDto;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.exception.ForbiddenException;
import ru.yegorr.musicstore.exception.ResourceIsNotFoundException;
import ru.yegorr.musicstore.service.TrackService;

@RestController
public class AudioController {
    private final TrackService trackService;

    private final UserChecker userChecker;

    @Autowired
    public AudioController(TrackService trackService, UserChecker userChecker) {
        this.trackService = trackService;
        this.userChecker = userChecker;
    }

    @PutMapping(path = "/track/{trackId}/audio",
            consumes = {"audio/mp3", "multipart/form-data"})
    public ResponseEntity<?> unloadAudio(@RequestParam("audio") MultipartFile audio, @PathVariable Long trackId,
                                         @RequestHeader("Authorization") String token) throws ApplicationException {
        if (!userChecker.isAdmin(token)) {
            throw new ForbiddenException("You have not rights for this");
        }
        trackService.saveAudio(audio, trackId);

        return ResponseBuilder.getBuilder().code(200).getResponseEntity();
    }

    @GetMapping(path = "/track/{trackId}/audio",
            produces = "audio/mp3")
    public ResponseEntity<?> getAudio(@PathVariable Long trackId, @RequestHeader("Authorization") String token)
            throws ApplicationException {
        userChecker.getUserId(token);

        byte[] audio = trackService.getAudio(trackId);
        if ((audio == null) || (audio.length == 0)) {
            throw new ResourceIsNotFoundException("The audio is not exist for this track");
        }
        return ResponseEntity.ok(audio);
    }

    @GetMapping(path = "/track/{trackId}")
    public ResponseEntity<?> getTrackInfo(@PathVariable Long trackId, @RequestHeader("Authorization") String token)
        throws ApplicationException {
        userChecker.getUserId(token);

        TrackResponseDto trackResponseDto = trackService.getTrackInfo(trackId);
        return ResponseBuilder.getBuilder().body(trackResponseDto).code(200).getResponseEntity();
    }
}
