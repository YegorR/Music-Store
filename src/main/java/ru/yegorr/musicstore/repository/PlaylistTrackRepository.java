package ru.yegorr.musicstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yegorr.musicstore.entity.PlaylistTrackEntity;

@Repository
public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrackEntity, Long> {
}
