package ru.yegorr.musicstore.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlbumResponseDto {
    private Long id;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date releaseDate;

    private boolean isSingle;

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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public void setSingle(boolean single) {
        isSingle = single;
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
