package ru.yegorr.musicstore.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "album")
public class AlbumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long albumId;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "release_year", nullable = false)
    private Date releaseYear;

    @Column(name = "cover")
    @Lob
    private byte[] cover;

    @Column(name = "is_single")
    private Boolean single;

    @ManyToOne
    @JoinColumn(name = "musician_id", nullable = false)
    private MusicianEntity musician;

    public AlbumEntity() {
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Date releaseYear) {
        this.releaseYear = releaseYear;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public Boolean getSingle() {
        return single;
    }

    public void setSingle(Boolean single) {
        this.single = single;
    }

    public MusicianEntity getMusician() {
        return musician;
    }

    public void setMusician(MusicianEntity musician) {
        this.musician = musician;
    }
}
