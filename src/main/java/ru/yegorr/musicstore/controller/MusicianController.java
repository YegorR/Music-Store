package ru.yegorr.musicstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class MusicianController {

    @PostMapping("/musician")
    public ResponseEntity<?> createMusician() {
        return null;
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
