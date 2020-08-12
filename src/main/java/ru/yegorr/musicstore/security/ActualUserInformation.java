package ru.yegorr.musicstore.security;

public class ActualUserInformation {
  private Long userId;

  private Boolean isAdmin;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Boolean getAdmin() {
    return isAdmin;
  }

  public void setAdmin(Boolean admin) {
    this.isAdmin = admin;
  }
}
