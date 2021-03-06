package ru.yegorr.musicstore.dto.response;

import ru.yegorr.musicstore.entity.PlaylistEntity;

public class BriefPlaylistDto {
  private Long id;

  private String name;

  public BriefPlaylistDto() {
  }

  public BriefPlaylistDto(PlaylistEntity entity) {
    id = entity.getPlaylistId();
    name = entity.getName();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
