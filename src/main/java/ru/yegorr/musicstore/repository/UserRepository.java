package ru.yegorr.musicstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yegorr.musicstore.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    int countAllByEmail(String email);

    int countAllByNickname(String nickname);
}
