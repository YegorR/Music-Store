package ru.yegorr.musicstore.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "favourite")
public class FavouriteEntity {
    @EmbeddedId
    private HistoryId historyId;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @MapsId("trackId")
    @ManyToOne
    @JoinColumn(name = "track_id")
    private TrackEntity track;

    @Column(name = "adding_time")
    private Timestamp addingTime;

    public FavouriteEntity() {
    }

    public HistoryId getHistoryId() {
        return historyId;
    }

    public void setHistoryId(HistoryId historyId) {
        this.historyId = historyId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity userEntity) {
        this.user = userEntity;
    }

    public TrackEntity getTrack() {
        return track;
    }

    public void setTrack(TrackEntity trackEntity) {
        this.track = trackEntity;
    }

    public Timestamp getAddingTime() {
        return addingTime;
    }

    public void setAddingTime(Timestamp playTime) {
        this.addingTime = playTime;
    }

    @Embeddable
    public static class HistoryId implements Serializable {
        private Integer userId;

        private Integer trackId;

        public HistoryId() {
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getTrackId() {
            return trackId;
        }

        public void setTrackId(Integer trackId) {
            this.trackId = trackId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HistoryId historyId = (HistoryId) o;
            return Objects.equals(userId, historyId.userId) &&
                    Objects.equals(trackId, historyId.trackId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, trackId);
        }
    }
}
