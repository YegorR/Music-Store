package ru.yegorr.musicstore.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "playlist")
public class PlaylistEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "playlist_id")
  private Long playlistId;

  @Column(name = "playlist_name")
  private String name;

  @Column(name = "playlist_image")
  @Basic(fetch = FetchType.LAZY)
  private byte[] image;

  @ManyToOne
  @JoinColumn(name = "user_id", updatable = false, insertable = false)
  private UserEntity user;

  @Column(name = "user_id")
  private Long userId;

  @OneToMany(mappedBy = "playlist")
  @OrderBy("order")
  private List<PlaylistTrackEntity> tracks;

  public PlaylistEntity() {
  }

  public Long getPlaylistId() {
    return playlistId;
  }

  public void setPlaylistId(Long playlistId) {
    this.playlistId = playlistId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public List<PlaylistTrackEntity> getTracks() {
    return tracks;
  }

  public void setTracks(List<PlaylistTrackEntity> tracks) {
    this.tracks = tracks;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
