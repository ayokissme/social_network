package com.social.network.repo;

import com.social.network.entity.TokenConfirm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenConfirmRepo extends JpaRepository<TokenConfirm, Long> {
    Optional<TokenConfirm> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE TokenConfirm c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.token = ?1")
    int updateConfirmedAt(String token,
                          LocalDateTime confirmedAt);
}
