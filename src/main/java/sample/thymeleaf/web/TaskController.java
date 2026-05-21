package sample.thymeleaf.web;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sample.common.dao.entity.Task;
import sample.service.TaskService;

@Controller
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
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
	    int totalPages = (int)Math.ceil((double) totalCount / pageSize); 
	    model.addAttribute("taskList", taskList);
	    model.addAttribute("page", page);
	    model.addAttribute("totalPages", totalPages);
	    return "tasks/list";
	    }
	
	 @GetMapping("/tasks/edit/{id}")
	 public String edit(@PathVariable Long id, Model model) {
		 Task task = taskService.getTaskById(id);
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
	 public String update(@PathVariable Long id,@ModelAttribute Task task, HttpSession session) {
		 String username = (String)session.getAttribute("username");
		 task.setId(id);
		 task.setUsername(username);
		 taskService.updateTask(task);
		 return "redirect:/tasks";
		 }
		 
	 @PostMapping("/tasks/delete/{id}")
	 public String delete(@PathVariable Long id) {
		 taskService.deleteTask(id);
		 return "redirect:/tasks";
		 }
}