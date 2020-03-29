package ru.yegorr.musicstore.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "playlist_track")
public class PlaylistTrackEntity {
    @EmbeddedId
    private PlaylistTrackId playlistTrackId;

    @MapsId("playlistId")
    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private PlaylistEntity playlist;

    @MapsId("trackId")
    @ManyToOne
    @JoinColumn(name = "track_id")
    private TrackEntity track;

    @MapsId("order")
    @Column(name = "order")
    private Integer order;

    public PlaylistTrackEntity() {
    }

    public PlaylistTrackId getPlaylistTrackId() {
        return playlistTrackId;
    }

    public void setPlaylistTrackId(PlaylistTrackId playlistTrackId) {
        this.playlistTrackId = playlistTrackId;
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

    @Embeddable
    public static class PlaylistTrackId implements Serializable {

        private Long playlistId;

        private Long trackId;

        private Integer order;

        public PlaylistTrackId() {
        }

        public Long getPlaylistId() {
            return playlistId;
        }

        public void setPlaylistId(Long playlist) {
            this.playlistId = playlist;
        }

        public Long getTrackId() {
            return trackId;
        }

        public void setTrackId(Long trackId) {
            this.trackId = trackId;
        }

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PlaylistTrackId that = (PlaylistTrackId) o;
            return Objects.equals(playlistId, that.playlistId) &&
                    Objects.equals(trackId, that.trackId) &&
                    Objects.equals(order, that.order);
        }

        @Override
        public int hashCode() {
            return Objects.hash(playlistId, trackId, order);
        }
    }
}
