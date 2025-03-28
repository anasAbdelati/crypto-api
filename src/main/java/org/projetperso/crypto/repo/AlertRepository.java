package org.projetperso.crypto.repo;

import org.projetperso.crypto.dto.Alert;
import org.projetperso.crypto.dto.AlertType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByUserIdAndCoinIdAndType(String userId, String coinId, AlertType type);

    List<Alert> findByActive(boolean active);

    List<Alert> findByUserId(String userId);

    Optional<Alert> findByIdAndUserId(Long id, String userId);
}
