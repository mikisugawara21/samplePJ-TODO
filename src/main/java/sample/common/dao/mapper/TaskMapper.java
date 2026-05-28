package sample.common.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import sample.common.dao.entity.Task;

@Mapper
public interface TaskMapper {
    List<Task> findAll(@Param("username") String username, @Param("offset") int offset, @Param("limit") int limit);
    Task findByIdAndUsername(@Param("id") Long id, @Param("username") String username);
    void insert(Task task);
    int update(Task task);
    int delete(@Param("id") Long id, @Param("username") String username);
    int countByUsername(String username);
}