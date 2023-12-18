package com.khagani.to_do_app.service;

import com.khagani.to_do_app.dto.TaskRequest;
import com.khagani.to_do_app.dto.TaskResponse;


import java.util.List;

public interface TaskService {
    void createTask(TaskRequest taskRequest);

    List<TaskResponse> getAllTask();

    TaskResponse getTaskById(Long id);

    void updateTask(Long id, TaskRequest taskRequest);

    void deleteTask(Long id);

}
