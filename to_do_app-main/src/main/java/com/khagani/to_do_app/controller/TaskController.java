package com.khagani.to_do_app.controller;
import com.khagani.to_do_app.dto.TaskRequest;
import com.khagani.to_do_app.dto.TaskResponse;
import com.khagani.to_do_app.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;



    @PostMapping
    public void createTask(@RequestBody @Valid TaskRequest taskRequest) {
        taskService.createTask(taskRequest);
    }


    @GetMapping
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTask();
    }


    @GetMapping("{id}")
    public TaskResponse getTask(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }


    @PutMapping("{id}")

    public void updateTask(@PathVariable Long id, @RequestBody TaskRequest taskRequest) {
        taskService.updateTask(id, taskRequest);

    }

    @DeleteMapping("{id}")
    public void deleteTask(@PathVariable Long id) { taskService.deleteTask(id);
    }



}
