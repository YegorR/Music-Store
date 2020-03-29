package ru.yegorr.musicstore.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "album")
public class AlbumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long albumId;

    @Column(name = "album_name", nullable = false, length = 64)
    private String name;

    @Column(name = "release_date", nullable = false)
    private Date releaseDate;

    @Column(name = "cover")
    @Lob
    private byte[] cover;

    @Column(name = "is_single")
    private Boolean single;

    @ManyToOne
    @JoinColumn(name = "musician_id", nullable = false)
    private MusicianEntity musician;

    @OneToMany(mappedBy = "album")
    private List<TrackEntity> albums;

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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseYear) {
        this.releaseDate = releaseYear;
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

    public List<TrackEntity> getAlbums() {
        return albums;
    }

    public void setAlbums(List<TrackEntity> albums) {
        this.albums = albums;
    }
}
