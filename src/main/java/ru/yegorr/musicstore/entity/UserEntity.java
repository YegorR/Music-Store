package ru.yegorr.musicstore.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_account")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "nickname", unique = true, nullable = false)
    private String nickname;

    @Column(name = "salt", nullable = false, length = 64)
    private String salt;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    @OneToMany(mappedBy = "user")
    private List<PlaylistEntity> playlists;

    @OneToMany(mappedBy = "user")
    private List<ReviewEntity> reviews;

    @OneToMany(mappedBy = "user")
    @OrderBy("playTime")
    private List<HistoryEntity> history;

    @OneToMany(mappedBy = "user")
    @OrderBy("addingTime")
    private List<FavouriteEntity> favourite;

    @ManyToMany
    @JoinTable(name = "subscription",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "musician_id"))
    private List<MusicianEntity> subscriptions;

    @ManyToMany
    @JoinTable(name = "subscription_notification",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "album_id"))
    private List<AlbumEntity> releaseAlbums;

    public UserEntity() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public List<PlaylistEntity> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<PlaylistEntity> playlists) {
        this.playlists = playlists;
    }

    public List<ReviewEntity> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewEntity> reviews) {
        this.reviews = reviews;
    }

    public List<HistoryEntity> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryEntity> history) {
        this.history = history;
    }

    public List<FavouriteEntity> getFavourite() {
        return favourite;
    }

    public void setFavourite(List<FavouriteEntity> favourite) {
        this.favourite = favourite;
    }

    public List<MusicianEntity> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<MusicianEntity> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<AlbumEntity> getReleaseAlbums() {
        return releaseAlbums;
    }

    public void setReleaseAlbums(List<AlbumEntity> releaseAlbums) {
        this.releaseAlbums = releaseAlbums;
    }
}
