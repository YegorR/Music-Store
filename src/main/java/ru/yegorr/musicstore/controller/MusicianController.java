package ru.yegorr.musicstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yegorr.musicstore.dto.MusicianDto;
import ru.yegorr.musicstore.dto.MusicianResponseDto;
import ru.yegorr.musicstore.dto.response.ResponseDto;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.exception.ForbiddenException;
import ru.yegorr.musicstore.service.MusicianService;

@RestController
public class MusicianController {

    private final MusicianService musicianService;

    private final UserChecker userChecker;

    @Autowired
    public MusicianController(MusicianService musicianService, UserChecker userChecker) {
        this.userChecker = userChecker;
        this.musicianService = musicianService;
    }

    @PostMapping(path = "/musician",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<?> createMusician(@RequestBody MusicianDto musicianDto,
                                            @RequestHeader("Authorization") String token) throws ApplicationException {
        if (!userChecker.isAdmin(token)) {
            throw new ForbiddenException("You have no rights to do this");
        }

        MusicianResponseDto musicianResponseDto =
                musicianService.createMusician(musicianDto.getName(), musicianDto.getDescription());

        ResponseDto responseDto = new ResponseDto();
        responseDto.setBody(musicianResponseDto);
        responseDto.setCode(201);
        return ResponseEntity.status(201).body(responseDto);
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

        ResponseDto responseDto = new ResponseDto();
        responseDto.setBody(musicianDto);
        responseDto.setCode(200);
        return ResponseEntity.status(200).body(responseDto);

    }

    @DeleteMapping("/musician/{musicianId}")
    public ResponseEntity<?> deleteMusician(@PathVariable("musicianId") Long musicianId,
                                            @RequestHeader("Authorization") String token) throws ApplicationException {
        if (!userChecker.isAdmin(token)) {
            throw new ForbiddenException("You have not rights to do this");
        }

        musicianService.deleteMusician(musicianId);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setCode(200);
        return ResponseEntity.status(200).body(responseDto);
    }

    @GetMapping("/musician/{musicianId}")
    public ResponseEntity<?> getMusician() {
        return null;
    }


}
