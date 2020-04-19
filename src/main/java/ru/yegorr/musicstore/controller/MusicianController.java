package ru.yegorr.musicstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yegorr.musicstore.dto.request.MusicianDto;
import ru.yegorr.musicstore.dto.response.MusicianResponseDto;
import ru.yegorr.musicstore.dto.response.ResponseBuilder;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.exception.ForbiddenException;
import ru.yegorr.musicstore.service.MusicianService;

@RestController
@RequestMapping("/musician")
public class MusicianController {

    private final MusicianService musicianService;

    private final UserChecker userChecker;

    @Autowired
    public MusicianController(MusicianService musicianService, UserChecker userChecker) {
        this.userChecker = userChecker;
        this.musicianService = musicianService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createMusician(@RequestBody MusicianDto musicianDto,
                                            @RequestHeader("Authorization") String token) throws ApplicationException {
        if (!userChecker.isAdmin(token)) {
            throw new ForbiddenException("You have no rights to do this");
        }

        MusicianResponseDto musicianResponseDto =
                musicianService.createMusician(musicianDto.getName(), musicianDto.getDescription());

        return ResponseBuilder.getBuilder().code(201).body(musicianResponseDto).getResponseEntity();
    }

    @PutMapping("/{musicianId}")
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

    @DeleteMapping("/{musicianId}")
    public ResponseEntity<?> deleteMusician(@PathVariable("musicianId") Long musicianId,
                                            @RequestHeader("Authorization") String token) throws ApplicationException {
        if (!userChecker.isAdmin(token)) {
            throw new ForbiddenException("You have not rights to do this");
        }

        musicianService.deleteMusician(musicianId);

        return ResponseBuilder.getBuilder().code(200).getResponseEntity();
    }

    @GetMapping("/{musicianId}")
    public ResponseEntity<?> getMusician(@PathVariable("musicianId") Long musicianId,
                                         @RequestHeader("Authorization") String token) throws ApplicationException {
        userChecker.getUserId(token);
        MusicianResponseDto musicianResponseDto = musicianService.getMusician(musicianId);
        return ResponseBuilder.getBuilder().code(200).body(musicianResponseDto).getResponseEntity();
    }
}
