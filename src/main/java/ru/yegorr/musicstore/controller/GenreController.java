package ru.yegorr.musicstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.request.GenreRequestDto;
import ru.yegorr.musicstore.dto.request.IdDto;
import ru.yegorr.musicstore.dto.response.GenreBriefResponseDto;
import ru.yegorr.musicstore.dto.response.GenreFullResponseDto;
import ru.yegorr.musicstore.dto.response.ResponseBuilder;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.exception.ForbiddenException;
import ru.yegorr.musicstore.service.GenreService;

import java.util.Base64;
import java.util.List;

@RestController
public class GenreController {

    private final GenreService genreService;

    private final UserChecker userChecker;

    @Autowired
    public GenreController(GenreService genreService, UserChecker userChecker) {
        this.genreService = genreService;
        this.userChecker = userChecker;
    }

    @PostMapping(path = "/genre")
    public ResponseEntity<?> createGenre(@RequestBody GenreRequestDto genre,
                                         @RequestHeader("Authorization") String token) throws ApplicationException {
        if (!userChecker.isAdmin(token)) {
            throw new ForbiddenException("You have no rights for this");
        }

        GenreFullResponseDto result = genreService.createGenre(genre.getName(), genre.getDescription());

        return ResponseBuilder.getBuilder().code(200).body(result).getResponseEntity();
    }

    @GetMapping(path = "/genre/{genreId}")
    public ResponseEntity<?> getGenre(@PathVariable Long genreId,
                                      @RequestHeader("Authorization") String token) throws ApplicationException {
        userChecker.getUserIdOrThrow(token);

        GenreFullResponseDto result = genreService.getGenre(genreId);

        return ResponseBuilder.getBuilder().code(200).body(result).getResponseEntity();
    }

    @GetMapping(path = "/genres")
    public ResponseEntity<?> getAllGenres(@RequestHeader("Authorization") String token,
                                          @RequestParam(required = false) String query) throws ApplicationException {
        if (query != null) {
            return searchGenres(query, token);
        }
        if (!userChecker.isAdmin(token)) {
            throw new ForbiddenException("You have no rights for this");
        }

        List<GenreFullResponseDto> result = genreService.getAllGenres();

        return ResponseBuilder.getBuilder().code(200).body(result).getResponseEntity();
    }

    private ResponseEntity<?> searchGenres(String query, String token) throws ApplicationException {
        userChecker.getUserIdOrThrow(token);

        List<GenreBriefResponseDto> result = genreService.searchGenres(query);

        return ResponseBuilder.getBuilder().body(result).code(200).getResponseEntity();
    }

    @PutMapping(path = "/genre/{genreId}")
    public ResponseEntity<?> changeGenre(@PathVariable Long genreId, @RequestBody GenreRequestDto genre,
                                         @RequestHeader("Authorization") String token) throws ApplicationException {
        if (!userChecker.isAdmin(token)) {
            throw new ForbiddenException("You have no rights for this");
        }
        GenreFullResponseDto result = genreService.changeGenre(genre.getName(), genre.getDescription(), genreId);

        return ResponseBuilder.getBuilder().code(200).body(result).getResponseEntity();
    }

    @DeleteMapping(path = "/genre/{genreId}")
    public ResponseEntity<?> changeGenre(@PathVariable Long genreId,
                                         @RequestHeader("Authorization") String token) throws ApplicationException {
        if (!userChecker.isAdmin(token)) {
            throw new ForbiddenException("You have no rights for this");
        }
        genreService.deleteGenre(genreId);

        return ResponseBuilder.getBuilder().code(200).getResponseEntity();
    }

    @PutMapping(path = "/audio/{trackId}/genre")
    public ResponseEntity<?> changeTrackGenres(@PathVariable Long trackId, @RequestBody List<IdDto> genres,
                                               @RequestHeader("Authorization") String token) throws ApplicationException {
        if (!userChecker.isAdmin(token)) {
            throw new ForbiddenException("You have no rights for this");
        }
        List<GenreBriefResponseDto> result = genreService.changeGenresOfTrack(trackId, genres);

        return ResponseBuilder.getBuilder().code(200).body(result).getResponseEntity();
    }

    @GetMapping(path = "/audio/{trackId}/genre")
    public ResponseEntity<?> getTrackGenres(@PathVariable Long trackId,
                                            @RequestHeader("Authorization") String token) throws ApplicationException {
        userChecker.getUserIdOrThrow(token);
        List<GenreBriefResponseDto> result = genreService.getGenresByTrack(trackId);

        return ResponseBuilder.getBuilder().code(200).body(result).getResponseEntity();
    }

    @PutMapping(path = "genre/{genreId}/image", consumes = "multipart/form-data")
    public ResponseEntity<?> unloadImage(@PathVariable Long genreId,
                                         @RequestHeader("Authorization") String token,
                                         @RequestParam("image")MultipartFile image) throws ApplicationException {
        if (!userChecker.isAdmin(token)) {
            throw new ForbiddenException("You have no rights for this");
        }

        genreService.saveImage(genreId, image);

        return ResponseBuilder.getBuilder().code(200).getResponseEntity();
    }

    @GetMapping(path = "genre/{genreId}/image",
            produces = "text/plain")
    public ResponseEntity<?> getCover(@PathVariable("genreId") Long genreId,
                                      @RequestHeader("Authorization") String token) throws ApplicationException {
        userChecker.getUserIdOrThrow(token);

        byte[] image = genreService.getImage(genreId);
        if (image == null) {
            image = new byte[0];
        }
        return ResponseEntity.ok(Base64.getEncoder().encodeToString(image));
    }

}
