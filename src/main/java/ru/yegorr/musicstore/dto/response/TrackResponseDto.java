package ru.yegorr.musicstore.dto.response;

public class TrackResponseDto {
    private Long id;

    private Long name;

    private Long albumId;

    private Long albumName;

    private Long musicianId;

    private Long musicianName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getName() {
        return name;
    }

    public void setName(Long name) {
        this.name = name;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Long getAlbumName() {
        return albumName;
    }

    public void setAlbumName(Long albumName) {
        this.albumName = albumName;
    }

    public Long getMusicianId() {
        return musicianId;
    }

    public void setMusicianId(Long musicianId) {
        this.musicianId = musicianId;
    }

    public Long getMusicianName() {
        return musicianName;
    }

    public void setMusicianName(Long musicianName) {
        this.musicianName = musicianName;
    }
}
