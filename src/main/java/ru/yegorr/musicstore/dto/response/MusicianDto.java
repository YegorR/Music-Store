package ru.yegorr.musicstore.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MusicianDto {
    private Long id;

    private String name;

    private String description;

    private List<AlbumDescriptionDto> albums = new ArrayList<>();

    private List<AlbumDescriptionDto> singles = new ArrayList<>();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicianDto that = (MusicianDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}