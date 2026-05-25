package sample.thymeleaf.web;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import sample.common.dao.entity.Task;
import sample.service.TaskService;

@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public String list(@RequestParam(defaultValue = "1") int page, HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        int pageSize = 10;
        int offset = (page - 1) * pageSize;
        List<Task> taskList = taskService.getTaskList(username, offset, pageSize);
        int totalCount = taskService.countByUsername(username);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        model.addAttribute("taskList", taskList);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", totalPages);
        return "tasks/list";
    }

    @GetMapping("/tasks/edit/{id}")
    public String edit(@PathVariable Long id, HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        Task task = taskService.getTaskByIdAndUsername(id, username);
        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("task", task);
        return "tasks/form-edit";
    }

    @GetMapping("/tasks/new")
    public String newTask(Model model) {
        model.addAttribute("task", new Task());
        return "tasks/form-new";
    }

    @PostMapping("/tasks")
    public String create(@ModelAttribute Task task, HttpSession session) {
        String username = (String) session.getAttribute("username");
        task.setUsername(username);
        taskService.createTask(task);
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Task task, HttpSession session) {
        String username = (String) session.getAttribute("username");
        task.setId(id);
        task.setUsername(username);
        int updated = taskService.updateTask(task);
        if (updated == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "task not found or not owned");
        }
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        String username = (String) session.getAttribute("username");
        int deleted = taskService.deleteTask(id, username);
        if (deleted == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "task not found or not owned");
        }
        return "redirect:/tasks";
    }
}