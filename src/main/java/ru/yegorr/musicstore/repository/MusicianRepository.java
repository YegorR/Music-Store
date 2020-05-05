package ru.yegorr.musicstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.yegorr.musicstore.entity.MusicianEntity;

import java.util.List;
import java.util.Map;

@Repository
public interface MusicianRepository extends JpaRepository<MusicianEntity, Long> {
    List<MusicianEntity> findAllByNameContainingIgnoreCase(String search);


    @Query("SELECT SUBSTRING(musician.name, 1, 1) as letter, COUNT(musician) as count " +
            "FROM MusicianEntity musician " +
            "GROUP BY SUBSTRING(musician.name, 1, 1)")
    List<MusicianAbcCount> getMusicianCountByAbc();
}
