package com.khagani.to_do_app.service.impl;

import com.khagani.to_do_app.dto.TaskRequest;
import com.khagani.to_do_app.dto.TaskResponse;
import com.khagani.to_do_app.entity.Task;
import com.khagani.to_do_app.exceptions.UserNotFoundException;
import com.khagani.to_do_app.mapper.TaskMapper;
import com.khagani.to_do_app.repository.TaskRepository;
import com.khagani.to_do_app.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskMapper taskMapper ;

    private final TaskRepository taskRepository;



    @Override
    public void createTask(TaskRequest taskRequest) {
        log.info("createTask().start");
        Task task = taskMapper.mapToTaskEntity(taskRequest);
        taskRepository.save(task);
        log.info("createdTask().end");
    }



    @Override
    public List<TaskResponse> getAllTask() {

        log.info("getAllTask().start");
        List<Task> task = taskRepository.findAll();
        List<TaskResponse> taskResponses = taskMapper.mapToTaskResponseList(task);
        log.info("getAllTask().end");
        return taskResponses;
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        log.info("getTaskById().start id {}", id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Task not founded by id " + id));
        log.info("getToDoById().end id {}", id);
        return taskMapper.mapToTaskResponse(task);

    }


    @Override
    public void updateTask(Long id, TaskRequest taskRequest) {
        log.info("updateTask().start id {} ", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Task not founded by id " + id));
        if (taskRequest.getName() != null)
            task.setName(taskRequest.getName());
        taskRepository.save(task);
        log.info("updateTask().end id {}", id);
    }

    @Override
    public void deleteTask(Long id) {
        log.info("deleteTask().start id ", id);
        taskRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Task not founded by id " + id));
        taskRepository.deleteById(id);
        log.info("deleteTask().end id ", id);

    }



}
