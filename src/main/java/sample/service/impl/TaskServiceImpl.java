package sample.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sample.common.dao.entity.Task;
import sample.common.dao.mapper.TaskMapper;
import sample.service.TaskService;
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskMapper taskMapper;
    @Override
    public List<Task> getTaskList(String username, int offset, int limit) {
        return taskMapper.findAll(username, offset, limit);
    }
    @Override
    public Task getTaskById(Long id) {
        return taskMapper.findById(id);
    }
    @Override
    public void createTask(Task task) {
        taskMapper.insert(task);
    }
    @Override
    public void updateTask(Task task) {
        taskMapper.update(task);
    }
    @Override
    public void deleteTask(Long id) {
        taskMapper.delete(id);
    }
    @Override
    public int countByUsername(String username) {
        return taskMapper.countByUsername(username);
    }
}