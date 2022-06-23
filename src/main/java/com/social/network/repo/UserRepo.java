package com.social.network.repo;

import com.social.network.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsUserByEmail(String email);

    @Query("select u from User u where u.email =:e or u.nickname =:n")
    List<User> findUsersByEmailOrNickname(@Param(value = "e") String email, @Param(value = "n") String nickname);
}
