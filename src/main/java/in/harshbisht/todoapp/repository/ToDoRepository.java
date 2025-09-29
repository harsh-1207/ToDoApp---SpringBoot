package in.harshbisht.todoapp.repository;

import in.harshbisht.todoapp.entity.ToDoEntity;
import in.harshbisht.todoapp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDoEntity, Long> {
    List<ToDoEntity> findByUser(UserEntity user);
}
