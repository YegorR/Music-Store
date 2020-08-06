package ru.yegorr.musicstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yegorr.musicstore.entity.FavouriteEntity;
import ru.yegorr.musicstore.entity.TrackEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface FavouriteRepository extends JpaRepository<FavouriteEntity, Long> {
    Optional<FavouriteEntity> findByUserIdAndTrackId(Long userId, Long trackId);

    void deleteByUserIdAndTrackId(Long userId, Long trackId);

    List<FavouriteEntity> findAllByUserIdOrderByAddingTimeDesc(Long userId);

    Set<FavouriteEntity> findAllByUserIdAndTrackIn(Long userId, List<TrackEntity> tracks);
}
