package ru.yegorr.musicstore.controller;

public class ActualUserInformation {
    private Long userId;

    private Boolean isAdmin;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        userId = userId;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
