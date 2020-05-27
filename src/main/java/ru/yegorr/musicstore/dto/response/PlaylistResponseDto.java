package ru.yegorr.musicstore.dto.response;

import java.util.List;

public class PlaylistResponseDto {
    private Long id;

    private String author;

    private String name;

    private List<TrackSuperFullResponseDto> tracks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TrackSuperFullResponseDto> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackSuperFullResponseDto> tracks) {
        this.tracks = tracks;
    }
}
