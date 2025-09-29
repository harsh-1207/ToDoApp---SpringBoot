package in.harshbisht.todoapp.controller;

import in.harshbisht.todoapp.entity.ToDoEntity;
import in.harshbisht.todoapp.entity.UserEntity;
import in.harshbisht.todoapp.repository.ToDoRepository;
import in.harshbisht.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoRepository toDoRepository;
    private final UserRepository userRepository;

    @GetMapping({"", "/", "/home"})
    public String showHomePage(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElseThrow();

        model.addAttribute("todos", toDoRepository.findByUser(user)); // ✅ Only user's tasks
        return "index";
    }


    @PostMapping("/add")
    public String add(@RequestParam String title) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElseThrow();

        ToDoEntity newTodo = ToDoEntity.builder()
                .title(title)
                .completed(false)
                .user(user) // ✅ Link to user
                .build();

        toDoRepository.save(newTodo);                       // save the current todo to the DB
        return "redirect:/";                                // to come back to the homepage
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id) {
        ToDoEntity existingTodo = toDoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found : " + id));
        existingTodo.setCompleted(true);
        toDoRepository.save(existingTodo);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        ToDoEntity existingTodo = toDoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found : " + id));
        toDoRepository.delete(existingTodo);
        return "redirect:/";
    }
}
