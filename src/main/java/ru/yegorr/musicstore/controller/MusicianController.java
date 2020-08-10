package ru.yegorr.musicstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.request.MusicianDto;
import ru.yegorr.musicstore.dto.response.MusicianBriefResponseDto;
import ru.yegorr.musicstore.dto.response.MusicianLetterResponseDto;
import ru.yegorr.musicstore.dto.response.MusicianResponseDto;
import ru.yegorr.musicstore.dto.response.ResponseBuilder;
import ru.yegorr.musicstore.exception.ClientException;
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
                                            @RequestHeader("Authorization") String token) throws Exception {
        userChecker.checkAdmin(token);

        MusicianResponseDto musicianResponseDto =
                musicianService.createMusician(musicianDto.getName(), musicianDto.getDescription());

        return ResponseBuilder.getBuilder().code(201).body(musicianResponseDto).getResponseEntity();
    }

    @PutMapping("/musician/{musicianId}")
    public ResponseEntity<?> changeMusician(@RequestBody MusicianDto musicianDto,
                                            @PathVariable("musicianId") Long musicianId,
                                            @RequestHeader("Authorization") String token) throws Exception {
        userChecker.checkAdmin(token);

        MusicianResponseDto musicianResponseDto = musicianService.changeMusician(musicianId, musicianDto.getName(),
                musicianDto.getDescription());

        return ResponseBuilder.getBuilder().body(musicianResponseDto).code(200).getResponseEntity();

    }

    @DeleteMapping("/musician/{musicianId}")
    public ResponseEntity<?> deleteMusician(@PathVariable("musicianId") Long musicianId,
                                            @RequestHeader("Authorization") String token) throws Exception {
        userChecker.checkAdmin(token);

        musicianService.deleteMusician(musicianId);

        return ResponseBuilder.getBuilder().code(200).getResponseEntity();
    }

    @GetMapping("/musician/{musicianId}")
    public ResponseEntity<?> getMusician(@PathVariable("musicianId") Long musicianId,
                                         @RequestHeader("Authorization") String token) throws Exception {
        userChecker.getUserIdOrThrow(token);
        MusicianResponseDto musicianResponseDto = musicianService.getMusician(musicianId);
        return ResponseBuilder.getBuilder().code(200).body(musicianResponseDto).getResponseEntity();
    }

    // TODO fix method
    @GetMapping("/musicians")
    public ResponseEntity<?> getMusiciansCountByLetter(
            @RequestParam(value = "abc", required = false) String isAbc,
            @RequestParam(value = "letter", required = false) String letter,
            @RequestParam(value = "query", required = false) String query,
            @RequestHeader("Authorization") String token) throws Exception {
        if (query != null) {
            userChecker.getUserIdOrThrow(token);
        } else {
            userChecker.checkAdmin(token);
        }
//        if (((isAbc == null) && (letter == null)) || ((isAbc != null) && (letter != null))) {
//            throw new ForbiddenException("Wrong method using");
//        }
        if (!isOnlyTrue((isAbc != null), (letter != null), (query != null))) {
            throw new ClientException("Wrong method using");
        }
        if (isAbc != null) {
            if (!isAbc.equals("true")) {
                throw new ClientException("Wrong method using");
            }

            Map<String, Integer> result = musicianService.getMusicianCountByAbc();
            return ResponseBuilder.getBuilder().code(200).body(result).getResponseEntity();
        } else if (letter != null) {
            List<MusicianLetterResponseDto> result = musicianService.getMusiciansByLetter(letter);
            return ResponseBuilder.getBuilder().code(200).body(result).getResponseEntity();
        } else {
            List<MusicianBriefResponseDto> result = musicianService.searchMusicians(query);
            return ResponseBuilder.getBuilder().code(200).body(result).getResponseEntity();
        }
    }

    private boolean isOnlyTrue(boolean... args) {
        boolean isOnly = false;
        for (boolean arg : args) {
            if (arg && (!isOnly)) {
                isOnly = true;
            } else if (arg && isOnly) {
                return false;
            }
        }
        return isOnly;
    }

    @PutMapping(path = "/musician/{musicianId}/image", consumes = "multipart/form-data")
    public ResponseEntity<?> loadImage(@PathVariable Long musicianId, @RequestHeader("Authorization") String token,
                                       @RequestParam("image") MultipartFile image) throws Exception {
        userChecker.checkAdmin(token);

        musicianService.saveImage(musicianId, image);
        return ResponseBuilder.getBuilder().code(200).getResponseEntity();
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
