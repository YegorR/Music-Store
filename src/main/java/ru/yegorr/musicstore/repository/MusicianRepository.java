package ru.yegorr.musicstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yegorr.musicstore.entity.MusicianEntity;

import java.util.List;

@Repository
public interface MusicianRepository extends JpaRepository<MusicianEntity, Long> {
    List<MusicianEntity> findAllByNameContainingIgnoreCase(String search);

    int countAllByNameStartingWithIgnoreCase(String letter);
}
