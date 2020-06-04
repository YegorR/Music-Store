package ru.yegorr.musicstore.dto.response;

import ru.yegorr.musicstore.entity.TrackEntity;

public class TrackSuperFullResponseDto {
    private Long id;

    private Long musicianId;

    private String musicianName;

    private Long albumId;

    private String albumName;

    private String name;

    private int playsNumber;

    private boolean favourite;

    public TrackSuperFullResponseDto() {
    }

    public TrackSuperFullResponseDto(TrackEntity entity, boolean isFavourite) {
        id = entity.getTrackId();
        musicianId = entity.getAlbum().getMusician().getMusicianId();
        musicianName = entity.getAlbum().getMusician().getName();
        albumId = entity.getAlbum().getAlbumId();
        albumName = entity.getAlbum().getName();
        name = entity.getName();
        playsNumber = entity.getPlaysNumber();
        favourite = isFavourite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMusicianId() {
        return musicianId;
    }

    public void setMusicianId(Long musicianId) {
        this.musicianId = musicianId;
    }

    public String getMusicianName() {
        return musicianName;
    }

    public void setMusicianName(String musicianName) {
        this.musicianName = musicianName;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlaysNumber() {
        return playsNumber;
    }

    public void setPlaysNumber(int playsNumber) {
        this.playsNumber = playsNumber;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
}
