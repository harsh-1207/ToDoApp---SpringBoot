package in.harshbisht.todoapp.controller;

import in.harshbisht.todoapp.entity.ToDoEntity;
import in.harshbisht.todoapp.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
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

    @GetMapping({"", "/", "/home"})
    public String showHomePage(Model model){
        model.addAttribute("todos", toDoRepository.findAll());
        return "index";
    }

    @PostMapping("/add")
    public String add(@RequestParam String title) {
        ToDoEntity newTodo = ToDoEntity.builder()
                                    .title(title)
                                    .completed(false)
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
