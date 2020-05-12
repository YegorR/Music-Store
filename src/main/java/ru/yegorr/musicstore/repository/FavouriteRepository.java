package ru.yegorr.musicstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yegorr.musicstore.entity.FavouriteEntity;
import ru.yegorr.musicstore.entity.TrackEntity;
import ru.yegorr.musicstore.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteRepository extends JpaRepository<FavouriteEntity, Long> {
    Optional<FavouriteEntity> findByUserIdAndTrackId(Long userId, Long trackId);

    void deleteByUserIdAndTrackId(Long userId, Long trackId);

    List<FavouriteEntity> findAllByUserId(Long userId);
}
