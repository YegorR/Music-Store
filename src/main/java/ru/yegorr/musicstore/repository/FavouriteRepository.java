package ru.yegorr.musicstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yegorr.musicstore.entity.FavouriteEntity;
import ru.yegorr.musicstore.entity.TrackEntity;
import ru.yegorr.musicstore.entity.UserEntity;

import java.util.Optional;

@Repository
public interface FavouriteRepository extends JpaRepository<FavouriteEntity, Long> {
    Optional<FavouriteEntity> findByUserAndTrack(UserEntity user, TrackEntity track);

    void deleteByUserAndTrack(UserEntity user, TrackEntity track);
}
