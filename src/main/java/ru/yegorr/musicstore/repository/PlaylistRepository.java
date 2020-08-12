package ru.yegorr.musicstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yegorr.musicstore.entity.PlaylistEntity;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Long> {
  List<PlaylistEntity> findAllByNameContainingIgnoreCase(String query);

  List<PlaylistEntity> findAllByUserId(Long userId);
}
