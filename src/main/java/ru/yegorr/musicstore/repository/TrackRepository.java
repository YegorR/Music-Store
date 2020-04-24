package ru.yegorr.musicstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yegorr.musicstore.entity.TrackEntity;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<TrackEntity, Long> {
    List<TrackEntity> findAllByNameContainingIgnoreCase(String search);
}
