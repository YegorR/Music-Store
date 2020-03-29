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
}
