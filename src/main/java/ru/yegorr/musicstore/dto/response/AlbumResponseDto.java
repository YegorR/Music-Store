package ru.yegorr.musicstore.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlbumResponseDto {
    private Long id;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate releaseDate;

    private boolean single;

    private MusicianBriefResponseDto musician;

    private List<TrackBriefResponseDto> tracks = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public MusicianBriefResponseDto getMusician() {
        return musician;
    }

    public void setMusician(MusicianBriefResponseDto musician) {
        this.musician = musician;
    }

    public List<TrackBriefResponseDto> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackBriefResponseDto> tracks) {
        this.tracks = tracks;
    }
}
