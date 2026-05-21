package sample.common.dao.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import sample.common.dao.entity.Task;
@Mapper
public interface TaskMapper {
    List<Task> findAll(@Param("username") String username, @Param("offset") int offset, @Param("limit") int limit);
    Task findById(Long id);
    void insert(Task task);
    void update(Task task);
    void delete(Long id);
    int countByUsername(String username);
}