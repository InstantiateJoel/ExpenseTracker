package de.ExpenseTracker.repository;

import de.ExpenseTracker.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IncomeRepository extends JpaRepository<Income, UUID> {
    List<Income> findByUser_Userid(UUID userUuid);
    Optional<Income> findByUser_UseridAndIncomeId(UUID userid, UUID incomeId);
    void deleteByUser_UseridAndIncomeId(UUID userId, UUID incomeId);
}