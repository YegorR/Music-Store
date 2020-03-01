package ru.yegorr.musicstore.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "playlist_track")
public class PlaylistTrackEntity {
    @EmbeddedId
    private PlaylistTrackId playlistTrackId;

    public PlaylistTrackEntity() {
    }

    public PlaylistTrackId getPlaylistTrackId() {
        return playlistTrackId;
    }

    public void setPlaylistTrackId(PlaylistTrackId playlistTrackId) {
        this.playlistTrackId = playlistTrackId;
    }


    @Embeddable
    public static class PlaylistTrackId implements Serializable {
        @ManyToOne
        @JoinColumn(name = "playlist_id")
        private PlaylistEntity playlist;

        @ManyToOne
        @JoinColumn(name = "track_id")
        private TrackEntity track;

        @Column(name = "order")
        private Integer order;

        public PlaylistTrackId() {
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PlaylistTrackId that = (PlaylistTrackId) o;
            return Objects.equals(playlist, that.playlist) &&
                    Objects.equals(track, that.track) &&
                    Objects.equals(order, that.order);
        }

        @Override
        public int hashCode() {
            return Objects.hash(playlist, track, order);
        }
    }
}
