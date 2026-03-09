package de.ExpenseTracker.repository;

import de.ExpenseTracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByParentIsNull(); // Top level categories
    List<Category> findByParent_CategoryId(UUID categoryId); // get subcategories for specific main category
}