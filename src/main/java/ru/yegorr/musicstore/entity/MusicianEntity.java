package ru.yegorr.musicstore.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "musician")
public class MusicianEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "musician_id")
  private Long musicianId;

  @Column(name = "musician_name", nullable = false, length = 64)
  private String name;

  @Column(name = "musician_description", length = 4096)
  private String description;

  @Column(name = "image")
  @Basic(fetch = FetchType.LAZY)
  private byte[] image;

  @OneToMany(mappedBy = "musician")
  @OrderBy("releaseDate")
  private List<AlbumEntity> albums;

  @ManyToMany(mappedBy = "subscriptions")
  private List<UserEntity> subscribers;

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

  public List<AlbumEntity> getAlbums() {
    return albums;
  }

  public void setAlbums(List<AlbumEntity> albums) {
    this.albums = albums;
  }

  public List<UserEntity> getSubscribers() {
    return subscribers;
  }

  public void setSubscribers(List<UserEntity> subscribers) {
    this.subscribers = subscribers;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MusicianEntity that = (MusicianEntity) o;
    return musicianId.equals(that.musicianId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(musicianId);
  }
}
