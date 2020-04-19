package ru.yegorr.musicstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yegorr.musicstore.entity.TrackEntity;

@Repository
public interface TrackRepository extends JpaRepository<TrackEntity, Long> {

}
