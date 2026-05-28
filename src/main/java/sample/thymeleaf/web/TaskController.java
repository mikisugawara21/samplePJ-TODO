package sample.thymeleaf.web;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sample.common.dao.entity.Task;
import sample.logic.exception.NotFoundException;
import sample.service.TaskService;
import sample.thymeleaf.web.form.TaskForm;

@Controller
public class TaskController {

    private final TaskService taskService;

    private static final int PAGE_SIZE = 10;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public String list(@RequestParam(defaultValue = "1") int page, HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        int totalCount = taskService.countByUsername(username);
        int totalPages = Math.max(1, (int) Math.ceil((double) totalCount / PAGE_SIZE));
        int safePage = Math.min(Math.max(page, 1), totalPages);
        int offset = (safePage - 1) * PAGE_SIZE;
        model.addAttribute("taskList", taskService.getTaskList(username, offset, PAGE_SIZE));
        model.addAttribute("page", safePage);
        model.addAttribute("totalPages", totalPages);
        return "tasks/list";
    }

    @GetMapping("/tasks/edit/{id}")
    public String edit(@PathVariable Long id, HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        Task task = taskService.getTaskByIdAndUsername(id, username);
        if (task == null) {
            throw new NotFoundException("task not found or not owned");
        }
        TaskForm form = new TaskForm();
        form.setTitle(task.getTitle());
        form.setContent(task.getContent());
        form.setName(task.getName());
        form.setStartDate(task.getStartDate());
        form.setEndDate(task.getEndDate());
        model.addAttribute("task", form);
        model.addAttribute("taskId", id);
        return "tasks/form-edit";
    }

    @GetMapping("/tasks/new")
    public String newTask(Model model) {
        model.addAttribute("task", new TaskForm());
        return "tasks/form-new";
    }

    @PostMapping("/tasks")
    public String create(@Validated @ModelAttribute("task") TaskForm form,
                         BindingResult br,
                         HttpSession session,
                         Model model) {
        if (br.hasErrors()) {
            return "tasks/form-new";
        }
        String username = (String) session.getAttribute("username");
        Task task = toEntity(form, username);
        taskService.createTask(task);
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/update/{id}")
    public String update(@PathVariable Long id,
                         @Validated @ModelAttribute("task") TaskForm form,
                         BindingResult br,
                         HttpSession session,
                         Model model) {
        if (br.hasErrors()) {
            model.addAttribute("taskId", id);
            return "tasks/form-edit";
        }
        String username = (String) session.getAttribute("username");
        Task task = toEntity(form, username);
        task.setId(id);
        int updated = taskService.updateTask(task);
        if (updated == 0) {
            throw new NotFoundException("task not found or not owned");
        }
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        String username = (String) session.getAttribute("username");
        int deleted = taskService.deleteTask(id, username);
        if (deleted == 0) {
            throw new NotFoundException("task not found or not owned");
        }
        return "redirect:/tasks";
    }

    private Task toEntity(TaskForm form, String username) {
        Task task = new Task();
        task.setUsername(username);
        task.setTitle(form.getTitle());
        task.setContent(form.getContent());
        task.setName(form.getName());
        task.setStartDate(form.getStartDate());
        task.setEndDate(form.getEndDate());
        return task;
    }
}