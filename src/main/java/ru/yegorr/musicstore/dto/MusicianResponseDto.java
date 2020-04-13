package ru.yegorr.musicstore.dto;

import java.util.List;

public class MusicianResponseDto {
    private Long id;

    private String name;

    private String description;

    private List<AlbumDescriptionDto> albums;

    private List<AlbumDescriptionDto> singles;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AlbumDescriptionDto> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumDescriptionDto> albums) {
        this.albums = albums;
    }

    public List<AlbumDescriptionDto> getSingles() {
        return singles;
    }

    public void setSingles(List<AlbumDescriptionDto> singles) {
        this.singles = singles;
    }
}