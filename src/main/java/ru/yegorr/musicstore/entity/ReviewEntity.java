package ru.yegorr.musicstore.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "review",
uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "album_id"}))
public class ReviewEntity {
    @Id
    @Column(name = "review_id")
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private AlbumEntity album;

    @Column(name = "title", length = 64, nullable = false)
    private String title;

    @Column(name = "review_text", length = 4096, nullable = false)
    private String text;

    @Column(name = "review_time", nullable = false)
    private Timestamp review_time;

    public ReviewEntity() {
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public AlbumEntity getAlbum() {
        return album;
    }

    public void setAlbum(AlbumEntity album) {
        this.album = album;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getReview_time() {
        return review_time;
    }

    public void setReview_time(Timestamp review_time) {
        this.review_time = review_time;
    }
}
