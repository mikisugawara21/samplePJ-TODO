package sample.service;

import java.util.List;

import sample.common.dao.entity.Task;

public interface TaskService {
    List<Task> getTaskList(String username, int offset, int limit);
    Task getTaskByIdAndUsername(Long id, String username);
    void createTask(Task task);
    int updateTask(Task task);
    int deleteTask(Long id, String username);
    int countByUsername(String username);
}