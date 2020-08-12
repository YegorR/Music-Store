package ru.yegorr.musicstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.response.TrackDto;
import ru.yegorr.musicstore.dto.response.TrackWithPlaysNumberDto;
import ru.yegorr.musicstore.exception.ClientException;
import ru.yegorr.musicstore.response.ResponseBuilder;
import ru.yegorr.musicstore.security.UserChecker;
import ru.yegorr.musicstore.service.TrackService;

import java.util.List;

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
  public ResponseEntity<?> unloadAudio(@RequestParam("audio") MultipartFile audio,
                                       @PathVariable Long trackId,
                                       @RequestHeader("Authorization") String token)
          throws Exception {
    userChecker.checkAdmin(token);
    trackService.saveAudio(audio, trackId);

    return ResponseBuilder.getBuilder().code(HttpStatus.OK).getResponseEntity();
  }

  @GetMapping(path = "/track/{trackId}/audio",
          produces = "audio/mp3")
  public ResponseEntity<?> getAudio(@PathVariable Long trackId,
                                    @RequestHeader("Authorization") String token)
          throws Exception {
    Long userId = userChecker.getUserIdOrThrow(token);

    byte[] audio = trackService.getAudio(trackId, userId);
    if ((audio == null) || (audio.length == 0)) {
      throw new ClientException(HttpStatus.NOT_FOUND, "The audio is not exist for this track");
    }
    return ResponseEntity.ok(audio);
  }

  @GetMapping(path = "/track/{trackId}")
  public ResponseEntity<?> getTrackInfo(@PathVariable Long trackId,
                                        @RequestHeader("Authorization") String token)
          throws Exception {
    userChecker.getUserIdOrThrow(token);

    TrackDto trackDto = trackService.getTrackInfo(trackId);
    return ResponseBuilder.getBuilder().body(trackDto).code(HttpStatus.OK).getResponseEntity();
  }

  @GetMapping(path = "/tracks")
  public ResponseEntity<?> searchTracks(@RequestParam String query,
                                        @RequestHeader("Authorization") String token)
          throws Exception {
    userChecker.getUserIdOrThrow(token);

    List<TrackWithPlaysNumberDto> result = trackService.searchTracks(query);
    return ResponseBuilder.getBuilder().body(result).code(HttpStatus.OK).getResponseEntity();
  }
}
