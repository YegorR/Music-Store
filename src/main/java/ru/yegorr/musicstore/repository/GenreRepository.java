package ru.yegorr.musicstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yegorr.musicstore.entity.GenreEntity;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
    List<GenreEntity> findByTracks_trackId(Long trackId);

    List<GenreEntity> findAllByNameContainsIgnoreCase(String query);
}
