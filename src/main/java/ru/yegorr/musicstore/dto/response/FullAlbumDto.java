package ru.yegorr.musicstore.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FullAlbumDto {
  private Long id;

  private String name;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
  private LocalDate releaseDate;

  private boolean single;

  private BriefMusicianDto musician;

  private List<TrackDescriptionDto> tracks = new ArrayList<>();

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

  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(LocalDate releaseDate) {
    this.releaseDate = releaseDate;
  }

  public boolean isSingle() {
    return single;
  }

  public void setSingle(boolean single) {
    this.single = single;
  }

  public BriefMusicianDto getMusician() {
    return musician;
  }

  public void setMusician(BriefMusicianDto musician) {
    this.musician = musician;
  }

  public List<TrackDescriptionDto> getTracks() {
    return tracks;
  }

  public void setTracks(List<TrackDescriptionDto> tracks) {
    this.tracks = tracks;
  }
}
