package sample.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import sample.common.dao.entity.Task;
import sample.common.dao.mapper.TaskMapper;
import sample.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public List<Task> getTaskList(String username, int offset, int limit) {
        return taskMapper.findAll(username, offset, limit);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskMapper.findById(id);
    }

    @Override
    public Task getTaskByIdAndUsername(Long id, String username) {
        return taskMapper.findByIdAndUsername(id, username);
    }

    @Override
    public void createTask(Task task) {
        taskMapper.insert(task);
    }

    @Override
    public int updateTask(Task task) {
        return taskMapper.update(task);
    }

    @Override
    public int deleteTask(Long id, String username) {
        return taskMapper.delete(id, username);
    }

    @Override
    public int countByUsername(String username) {
        return taskMapper.countByUsername(username);
    }
}