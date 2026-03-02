package de.ExpenseTracker.repository;

import de.ExpenseTracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    List<Expense> findByUser_Userid(UUID userid);
    List<Expense> findByUser_UseridAndCategory_CategoryId(UUID userid, UUID categoryId);
}
