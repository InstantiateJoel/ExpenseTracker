package de.ExpenseTracker.repository;

import de.ExpenseTracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    @Query("SELECT e FROM Expense e WHERE e.user.userid = :userid AND YEAR(e.paymentDate) = 2026 AND MONTH(e.paymentDate) = MONTH(CURRENT_DATE)")
    List<Expense> findCurrentMonthExpensesByUser(@Param("userid") UUID userid);

    @Query("SELECT e FROM Expense e WHERE e.user.userid = :userid ORDER BY e.paymentDate ASC LIMIT 1")
    Optional<Expense> findFirstExpenseByUser(@Param("userid") UUID userid);

    @Query ("SELECT e FROM Expense e WHERE e.user.userid = :userid  and year(e.paymentDate) = :year and MONTH(e.paymentDate) = :month")
    List<Expense> findByUser_UseridAndYearAndMonth(@Param("userid")  UUID userid, @Param("year") Integer year, @Param("month") Integer month);

    List<Expense> findByUser_UseridAndCategory_CategoryId(UUID userid, UUID categoryId);
    Optional<Expense> findByUser_UseridAndExpenseId(UUID userid, UUID expenseId);
    void deleteByUser_UseridAndExpenseId(UUID userId, UUID expenseId);
}