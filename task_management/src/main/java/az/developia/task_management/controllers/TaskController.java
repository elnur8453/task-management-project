package az.developia.task_management.controllers;

import az.developia.task_management.dtos.request.CreateTaskRequest;
import az.developia.task_management.dtos.request.UpdateTaskRequest;
import az.developia.task_management.dtos.request.UpdateTaskStatusRequest;
import az.developia.task_management.dtos.response.GetAllTasksResponse;
import az.developia.task_management.services.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/categories/tasks")
@AllArgsConstructor
public class TaskController {
    private TaskService taskService;

    @PostMapping
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADD_TASK')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createTask(@Valid @RequestBody CreateTaskRequest createTaskRequest){

        taskService.addTask(createTaskRequest);
    }

    @GetMapping
    @PreAuthorize(value = "hasAuthority('ROLE_GET_TASK')")
    public GetAllTasksResponse getAllTasks(
            @RequestParam(name = "begin", required = false, defaultValue = "0") Integer begin,
            @RequestParam(name = "length", required = false, defaultValue = "10") Integer length) {

        return taskService.getAll(begin, length);
    }

    @GetMapping("/by-date-range")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_GET_TASK')")
    public GetAllTasksResponse getAllTasksByDateRange(
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(name = "begin", required = false, defaultValue = "0") Integer begin,
            @RequestParam(name = "length", required = false, defaultValue = "10") Integer length
    ){

        return taskService.getAllTasksByDateRange(startDate, endDate, begin, length);
    }

    @GetMapping("/by-date-range-and-category")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_GET_TASK')")
    public GetAllTasksResponse getAllTasksByDateRange(
            @RequestParam(name = "category_id") Integer categoryId,
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(name = "begin", required = false, defaultValue = "0") Integer begin,
            @RequestParam(name = "length", required = false, defaultValue = "10") Integer length
    ){

        return taskService.getAllTasksByDateRangeAndCategory(categoryId, startDate, endDate, begin, length);
    }

    @GetMapping("/completed")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_GET_TASK')")
    public GetAllTasksResponse getAllCompletedTasks(
            @RequestParam(name = "begin", required = false, defaultValue = "0") Integer begin,
            @RequestParam(name = "length", required = false, defaultValue = "10") Integer length
    ){

        return taskService.getAllCompletedTasks(begin, length);
    }

    @GetMapping("/uncompleted")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_GET_TASK')")
    public GetAllTasksResponse getAllUncompletedTasks(
            @RequestParam(name = "begin", required = false, defaultValue = "0") Integer begin,
            @RequestParam(name = "length", required = false, defaultValue = "10") Integer length
    ){

        return taskService.getAllUncompletedTasks(begin, length);
    }

    @GetMapping("/by-description")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_GET_TASK')")
    public GetAllTasksResponse getAllTasksByDescription(
            @RequestParam(name = "description") String description,
            @RequestParam(name = "begin", required = false, defaultValue = "0") Integer begin,
            @RequestParam(name = "length", required = false, defaultValue = "10") Integer length) {

        return taskService.getAllByDescription(description, begin, length);
    }

    @GetMapping("/overdue")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_GET_TASK')")
    public GetAllTasksResponse getAllOverdueTasks(
            @RequestParam(name = "begin", required = false, defaultValue = "0") Integer begin,
            @RequestParam(name = "length", required = false, defaultValue = "10") Integer length
    ){

        return taskService.getAllOverdueTasks(begin, length);
    }

    @PutMapping("/update-status")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_UPDATE_TASK_STATUS')")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateTaskStatus(@RequestBody UpdateTaskStatusRequest updateTaskStatusRequest){

        taskService.updateTaskStatus(updateTaskStatusRequest);
    }

    @PutMapping
    @PreAuthorize(value = "hasAnyAuthority('ROLE_UPDATE_TASK')")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateTask(@RequestBody UpdateTaskRequest updateTaskRequest){

        taskService.updateTask(updateTaskRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_DELETE_TASK')")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteById(@PathVariable Integer id) {

        taskService.delete(id);
    }
}
