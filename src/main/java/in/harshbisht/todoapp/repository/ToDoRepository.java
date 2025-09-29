package in.harshbisht.todoapp.repository;

import in.harshbisht.todoapp.entity.ToDoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<ToDoEntity, Long> {
}
