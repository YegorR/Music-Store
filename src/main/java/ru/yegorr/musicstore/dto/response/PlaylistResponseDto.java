package ru.yegorr.musicstore.dto.response;

import ru.yegorr.musicstore.entity.PlaylistEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class PlaylistResponseDto {
    private Long id;

    private String author;

    private String name;

    private List<TrackSuperFullResponseDto> tracks;

    public PlaylistResponseDto() {
    }

    public PlaylistResponseDto(PlaylistEntity entity, Iterator<Boolean> isFavouriteIterator) {
        id = entity.getPlaylistId();
        author = entity.getUser().getNickname();
        name = entity.getName();
        tracks = entity.getTracks().stream().
                map(track -> new TrackSuperFullResponseDto(track.getTrack(), isFavouriteIterator.next())).
                collect(Collectors.toList());
    }

    public PlaylistResponseDto(PlaylistEntity entity, String authorName) {
        id = entity.getPlaylistId();
        author = authorName;
        name = entity.getName();
        tracks = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TrackSuperFullResponseDto> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackSuperFullResponseDto> tracks) {
        this.tracks = tracks;
    }
}
