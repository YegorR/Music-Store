package ru.yegorr.musicstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yegorr.musicstore.dto.MusicianDto;
import ru.yegorr.musicstore.dto.MusicianResponseDto;
import ru.yegorr.musicstore.dto.ResponseDto;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.exception.ForbiddenException;
import ru.yegorr.musicstore.service.AuthService;
import ru.yegorr.musicstore.service.MusicianService;

@RestController
public class MusicianController {

    private final MusicianService musicianService;

    private final AuthService authService;

    public MusicianController(MusicianService musicianService, AuthService authService) {
        this.authService = authService;
        this.musicianService = musicianService;
    }

    @PostMapping(path = "/musician",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<?> createMusician(@RequestBody MusicianDto musicianDto,
                                            @RequestHeader("Authorization") String token) throws ApplicationException {
        ActualUserInformation userInformation = authService.check(token);
        if (!userInformation.getAdmin()) {
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
    public ResponseEntity<?> changeMusician() {
        return null;
    }

    @DeleteMapping("/musician/{musicianId}")
    public ResponseEntity<?> deleteMusician() {
        return null;
    }

    @GetMapping("/musician/{musicianId}")
    public ResponseEntity<?> getMusician() {
        return null;
    }


}
