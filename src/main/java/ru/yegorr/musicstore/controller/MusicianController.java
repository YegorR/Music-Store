package ru.yegorr.musicstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.request.MusicianDto;
import ru.yegorr.musicstore.dto.response.MusicianLetterResponseDto;
import ru.yegorr.musicstore.dto.response.MusicianResponseDto;
import ru.yegorr.musicstore.dto.response.ResponseBuilder;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.exception.ForbiddenException;
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
    public ResponseEntity<?> createMusician(@RequestBody MusicianDto musicianDto,
                                            @RequestHeader("Authorization") String token) throws ApplicationException {
        if (!userChecker.isAdmin(token)) {
            throw new ForbiddenException("You have no rights to do this");
        }

        MusicianResponseDto musicianResponseDto =
                musicianService.createMusician(musicianDto.getName(), musicianDto.getDescription());

        return ResponseBuilder.getBuilder().code(201).body(musicianResponseDto).getResponseEntity();
    }

    @PutMapping("/musician/{musicianId}")
    public ResponseEntity<?> changeMusician(@RequestBody MusicianDto musicianDto,
                                            @PathVariable("musicianId") Long musicianId,
                                            @RequestHeader("Authorization") String token) throws ApplicationException {
        if (!userChecker.isAdmin(token)) {
            throw new ForbiddenException("You have no rights to do this");
        }

        MusicianResponseDto musicianResponseDto = musicianService.changeMusician(musicianId, musicianDto.getName(),
                musicianDto.getDescription());

        return ResponseBuilder.getBuilder().body(musicianDto).code(200).getResponseEntity();

    }

    @DeleteMapping("/musician/{musicianId}")
    public ResponseEntity<?> deleteMusician(@PathVariable("musicianId") Long musicianId,
                                            @RequestHeader("Authorization") String token) throws ApplicationException {
        if (!userChecker.isAdmin(token)) {
            throw new ForbiddenException("You have not rights to do this");
        }

        musicianService.deleteMusician(musicianId);

        return ResponseBuilder.getBuilder().code(200).getResponseEntity();
    }

    @GetMapping("/musician/{musicianId}")
    public ResponseEntity<?> getMusician(@PathVariable("musicianId") Long musicianId,
                                         @RequestHeader("Authorization") String token) throws ApplicationException {
        userChecker.getUserIdOrThrow(token);
        MusicianResponseDto musicianResponseDto = musicianService.getMusician(musicianId);
        return ResponseBuilder.getBuilder().code(200).body(musicianResponseDto).getResponseEntity();
    }

    @GetMapping("/musicians")
    public ResponseEntity<?> getMusiciansCountByLetter(
            @RequestParam(value = "abc", required = false) String isAbc,
            @RequestParam(value = "letter", required = false) String letter,
            @RequestHeader("Authorization") String token) throws ApplicationException {
        if (!userChecker.isAdmin(token)) {
            throw new ForbiddenException("You have not rights to do this");
        }
        if (((isAbc == null) && (letter == null)) || ((isAbc != null) && (letter != null))) {
            throw new ForbiddenException("Wrong method using");
        }
        if (isAbc != null) {
            if (!isAbc.equals("true")) {
                throw new ApplicationException("Wrong method using");
            }

            Map<String, Integer> result = musicianService.getMusicianCountByAbc();
            return ResponseBuilder.getBuilder().code(200).body(result).getResponseEntity();
        } else {
            List<MusicianLetterResponseDto> result = musicianService.getMusiciansByLetter(letter);
            return ResponseBuilder.getBuilder().code(200).body(result).getResponseEntity();
        }
    }

    @PutMapping(path = "/musician/{musicianId}/image", consumes = "multipart/form-data")
    public ResponseEntity<?> loadImage(@PathVariable Long musicianId, @RequestHeader("Authorization") String token,
                                       @RequestParam("image") MultipartFile image) throws ApplicationException {
        if (!userChecker.isAdmin(token)) {
            throw new ForbiddenException("You have no rights for this");
        }

        musicianService.saveImage(musicianId, image);
        return ResponseBuilder.getBuilder().code(200).getResponseEntity();
    }

    @GetMapping(path = "/musician/{musicianId}/image", produces = "text/plain")
    public ResponseEntity<?> getImage(@PathVariable Long musicianId,
                                      @RequestHeader("Authorization") String token) throws ApplicationException {
        userChecker.getUserIdOrThrow(token);

        byte[] image = musicianService.getImage(musicianId);
        return ResponseEntity.ok(Base64.getEncoder().encodeToString(image));
    }
}
