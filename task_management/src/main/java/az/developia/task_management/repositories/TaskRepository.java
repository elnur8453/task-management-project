package az.developia.task_management.repositories;

import az.developia.task_management.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query(value = "SELECT * FROM tasks WHERE owner = ?1 ORDER BY created_date DESC LIMIT ?3 OFFSET ?2", nativeQuery = true)
    List<Task> findAll(String username, Integer begin, Integer length);

    @Query(value = "SELECT * FROM tasks WHERE owner = ?1 AND created_date BETWEEN ?2 AND ?3 ORDER BY created_date DESC LIMIT ?5 OFFSET ?4", nativeQuery = true)
    List<Task> findAllByDateRange(String username, LocalDateTime startDate, LocalDateTime endDate, Integer begin, Integer length);

    @Query(value = "SELECT * FROM tasks WHERE owner = ?1 AND category_id = ?2 AND created_date BETWEEN ?3 AND ?4 ORDER BY created_date DESC LIMIT ?6 OFFSET ?5", nativeQuery = true)
    List<Task> findAllByDateRangeAndCategory(String username, Integer categoryId, LocalDateTime startDate, LocalDateTime endDate, Integer begin, Integer length);

    @Query(value = "SELECT * FROM tasks WHERE owner = ?1 AND completed = true ORDER BY created_date DESC LIMIT ?3 OFFSET ?2", nativeQuery = true)
    List<Task> findAllCompleted(String username, Integer begin, Integer length);

    @Query(value = "SELECT * FROM tasks WHERE owner = ?1 AND completed = false ORDER BY created_date DESC LIMIT ?3 OFFSET ?2", nativeQuery = true)
    List<Task> findAllUncompleted(String username, Integer begin, Integer length);

    @Query(value = "SELECT * FROM tasks WHERE owner = ?1 AND description = ?2 ORDER BY created_date DESC LIMIT ?4 OFFSET ?3", nativeQuery = true)
    List<Task> findAllByDescription(String username, String description, Integer begin, Integer length);

    @Query(value = "SELECT * FROM tasks WHERE owner = ?1 AND completed = false AND created_date + due_days * interval '1 day' < CURRENT_TIMESTAMP ORDER BY created_date DESC LIMIT ?3 OFFSET ?2", nativeQuery = true)
    List<Task> findAllOverdue(String username, Integer begin, Integer length);

    Optional<Task> findByDescription(String description);
}
