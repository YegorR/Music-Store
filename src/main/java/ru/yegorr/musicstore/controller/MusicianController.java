package ru.yegorr.musicstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.response.BriefMusicianDto;
import ru.yegorr.musicstore.dto.response.MusicianLetterDto;
import ru.yegorr.musicstore.dto.response.MusicianDto;
import ru.yegorr.musicstore.response.ResponseBuilder;
import ru.yegorr.musicstore.security.UserChecker;
import ru.yegorr.musicstore.service.MusicianService;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
public class MusicianController {

    private final MusicianService musicianService;

    private final UserChecker userChecker;

    @Autowired
    public MusicianController(MusicianService musicianService, UserChecker userChecker) {
        this.userChecker = userChecker;
        this.musicianService = musicianService;
    }

    @PostMapping(path = "/musician", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createMusician(@RequestBody ru.yegorr.musicstore.dto.request.MusicianDto musicianDto,
                                            @RequestHeader("Authorization") String token) throws Exception {
        userChecker.checkAdmin(token);

        MusicianDto musicianResponseDto =
                musicianService.createMusician(musicianDto.getName(), musicianDto.getDescription());

        return ResponseBuilder.getBuilder().code(HttpStatus.CREATED).body(musicianResponseDto).getResponseEntity();
    }

    @PutMapping("/musician/{musicianId}")
    public ResponseEntity<?> changeMusician(@RequestBody ru.yegorr.musicstore.dto.request.MusicianDto musicianDto,
                                            @PathVariable("musicianId") Long musicianId,
                                            @RequestHeader("Authorization") String token) throws Exception {
        userChecker.checkAdmin(token);

        MusicianDto musicianResponseDto = musicianService.changeMusician(musicianId, musicianDto.getName(),
                musicianDto.getDescription());

        return ResponseBuilder.getBuilder().body(musicianResponseDto).code(HttpStatus.OK).getResponseEntity();

    }

    @DeleteMapping("/musician/{musicianId}")
    public ResponseEntity<?> deleteMusician(@PathVariable("musicianId") Long musicianId,
                                            @RequestHeader("Authorization") String token) throws Exception {
        userChecker.checkAdmin(token);

        musicianService.deleteMusician(musicianId);

        return ResponseBuilder.getBuilder().code(HttpStatus.NO_CONTENT).getResponseEntity();
    }

    @GetMapping("/musician/{musicianId}")
    public ResponseEntity<?> getMusician(@PathVariable("musicianId") Long musicianId,
                                         @RequestHeader("Authorization") String token) throws Exception {
        userChecker.getUserIdOrThrow(token);
        MusicianDto musicianDto = musicianService.getMusician(musicianId);
        return ResponseBuilder.getBuilder().code(HttpStatus.OK).body(musicianDto).getResponseEntity();
    }

    @GetMapping("/musicians")
    public ResponseEntity<?> searchMusicians(@RequestParam("query") String query,
                                             @RequestHeader("Authorization") String token) throws Exception {
        userChecker.getUserIdOrThrow(token);

        List<BriefMusicianDto> result = musicianService.searchMusicians(query);
        return ResponseBuilder.getBuilder().code(HttpStatus.OK).body(result).getResponseEntity();
    }

    @GetMapping("/musicians/abc")
    public ResponseEntity<?> getMusiciansAbcList(@RequestHeader("Authorization") String token) throws Exception {
        userChecker.checkAdmin(token);
        Map<String, Integer> result = musicianService.getMusicianCountByAbc();
        return ResponseBuilder.getBuilder().code(HttpStatus.OK).body(result).getResponseEntity();
    }

    @GetMapping("/musicians/letter")
    public ResponseEntity<?> getMusiciansByLetter(@RequestParam("letter") String letter,
                                                  @RequestHeader("Authorization") String token) throws Exception {
        userChecker.checkAdmin(token);
        List<MusicianLetterDto> result = musicianService.getMusiciansByLetter(letter);
        return ResponseBuilder.getBuilder().code(HttpStatus.OK).body(result).getResponseEntity();
    }

    @PutMapping(path = "/musician/{musicianId}/image", consumes = "multipart/form-data")
    public ResponseEntity<?> loadImage(@PathVariable Long musicianId, @RequestHeader("Authorization") String token,
                                       @RequestParam("image") MultipartFile image) throws Exception {
        userChecker.checkAdmin(token);

        musicianService.saveImage(musicianId, image);
        return ResponseBuilder.getBuilder().code(HttpStatus.OK).getResponseEntity();
    }

    @GetMapping(path = "/musician/{musicianId}/image", produces = "text/plain")
    public ResponseEntity<?> getImage(@PathVariable Long musicianId,
                                      @RequestHeader("Authorization") String token) throws Exception {
        userChecker.getUserIdOrThrow(token);

        byte[] image = musicianService.getImage(musicianId);
        if (image == null) {
            image = new byte[0];
        }
        return ResponseEntity.ok(Base64.getEncoder().encodeToString(image));
    }
}
