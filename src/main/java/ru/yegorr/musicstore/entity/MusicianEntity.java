package ru.yegorr.musicstore.entity;

import javax.persistence.*;

@Entity
@Table(name = "musician")
public class MusicianEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "musician_id")
    private Long musicianId;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "description", length = 4096)
    private String description;

    @Lob
    @Column(name = "image")
    private byte[] image;

    public MusicianEntity() {
    }

    public Long getMusicianId() {
        return musicianId;
    }

    public void setMusicianId(Long musicianId) {
        this.musicianId = musicianId;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
