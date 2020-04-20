package ru.yegorr.musicstore.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrackBriefResponseDto {
    private Long id;

    private String name;

    private int playsNumber;

    @JsonProperty("isFavourite")
    private boolean isFavourite;

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

    public int getPlaysNumber() {
        return playsNumber;
    }

    public void setPlaysNumber(int playsNumber) {
        this.playsNumber = playsNumber;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
