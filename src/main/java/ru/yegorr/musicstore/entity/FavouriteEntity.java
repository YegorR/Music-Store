package ru.yegorr.musicstore.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "favourite")
public class FavouriteEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "favourite_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private UserEntity user;

  @Column(name = "user_id")
  private Long userId;

  @ManyToOne
  @JoinColumn(name = "track_id", insertable = false, updatable = false)
  private TrackEntity track;

  @Column(name = "track_id")
  private Long trackId;

  @Column(name = "adding_time")
  private LocalDateTime addingTime;

  public FavouriteEntity() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public TrackEntity getTrack() {
    return track;
  }

  public void setTrack(TrackEntity track) {
    this.track = track;
  }

  public LocalDateTime getAddingTime() {
    return addingTime;
  }

  public void setAddingTime(LocalDateTime addingTime) {
    this.addingTime = addingTime;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getTrackId() {
    return trackId;
  }

  public void setTrackId(Long trackId) {
    this.trackId = trackId;
  }
}
