package ru.yegorr.musicstore.dto.response;

import ru.yegorr.musicstore.entity.GenreEntity;

public class GenreFullResponseDto {
    private Long id;

    private String name;

    private String description;

    public GenreFullResponseDto() {
    }

    public GenreFullResponseDto(GenreEntity genreEntity) {
        id = genreEntity.getGenreId();
        name = genreEntity.getName();
        description = genreEntity.getDescription();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
