package sample.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sample.common.dao.entity.Task;
import sample.common.dao.mapper.TaskMapper;
import sample.service.TaskService;

@Service
@Transactional(readOnly = true)
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
    public Task getTaskByIdAndUsername(Long id, String username) {
        return taskMapper.findByIdAndUsername(id, username);
    }

    @Override
    @Transactional
    public void createTask(Task task) {
        taskMapper.insert(task);
    }

    @Override
    @Transactional
    public int updateTask(Task task) {
        return taskMapper.update(task);
    }

    @Override
    @Transactional
    public int deleteTask(Long id, String username) {
        return taskMapper.delete(id, username);
    }

    @Override
    public int countByUsername(String username) {
        return taskMapper.countByUsername(username);
    }
}