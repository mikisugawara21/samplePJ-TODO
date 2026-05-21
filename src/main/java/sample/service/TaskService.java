package sample.service;

import java.util.List;

import sample.common.dao.entity.Task;

public interface TaskService {
	List<Task> getTaskList(String username, int offset, int limit);
	Task getTaskById(Long id);
	void createTask(Task task);
	void updateTask(Task task);
	void deleteTask(Long id);
	int countByUsername(String username);
}
