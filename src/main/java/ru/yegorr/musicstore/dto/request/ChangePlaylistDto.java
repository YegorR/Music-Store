package ru.yegorr.musicstore.dto.request;

import java.util.List;

public class ChangePlaylistDto {
  private String name;

  private List<IdDto> tracks;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<IdDto> getTracks() {
    return tracks;
  }

  public void setTracks(List<IdDto> tracks) {
    this.tracks = tracks;
  }
}
