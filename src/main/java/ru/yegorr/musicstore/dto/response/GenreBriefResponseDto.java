package ru.yegorr.musicstore.dto.response;

import ru.yegorr.musicstore.entity.GenreEntity;

public class GenreBriefResponseDto {
    private Long id;

    private String name;

    public GenreBriefResponseDto(){

    }

    public GenreBriefResponseDto(GenreEntity entity) {
        id = entity.getGenreId();
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
