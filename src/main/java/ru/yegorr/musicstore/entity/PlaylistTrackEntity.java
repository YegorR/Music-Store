package ru.yegorr.musicstore.entity;

import javax.persistence.*;

@Entity
@Table(name = "playlist_track")
public class PlaylistTrackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_track_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private PlaylistEntity playlist;

    @ManyToOne
    @JoinColumn(name = "track_id")
    private TrackEntity track;

    @Column(name = "order")
    private Integer order;

    public PlaylistTrackEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlaylistEntity getPlaylist() {
        return playlist;
    }

    public void setPlaylist(PlaylistEntity playlist) {
        this.playlist = playlist;
    }

    public TrackEntity getTrack() {
        return track;
    }

    public void setTrack(TrackEntity track) {
        this.track = track;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
