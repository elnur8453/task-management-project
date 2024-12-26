package az.developia.task_management.repositories;

import az.developia.task_management.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "SELECT * FROM categories WHERE owner = ?1 LIMIT ?3 OFFSET ?2", nativeQuery = true)
    List<Category> findAll(String username, Integer begin, Integer length);

    Optional<Category> findByName(String name);
}
