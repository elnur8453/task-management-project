package az.developia.task_management.services;

import az.developia.task_management.dtos.request.CreateTaskRequest;
import az.developia.task_management.dtos.request.UpdateTaskRequest;
import az.developia.task_management.dtos.request.UpdateTaskStatusRequest;
import az.developia.task_management.dtos.response.GetAllTasksResponse;
import az.developia.task_management.dtos.response.TaskResponse;
import az.developia.task_management.entities.Task;
import az.developia.task_management.exceptions.OurRuntimeException;
import az.developia.task_management.repositories.TaskRepository;
import az.developia.task_management.utilities.Methods;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;
    private ModelMapper modelMapper;

    public GetAllTasksResponse getAll(Integer begin, Integer length) {
        String username = Methods.findActiveUsername();

        List<Task> tasks = taskRepository.findAll(username, begin, length);
        List<TaskResponse> taskResponses = new ArrayList<>();

        for (Task task : tasks) {
            TaskResponse item = new TaskResponse();
            modelMapper.map(task, item);
            taskResponses.add(item);
        }

        GetAllTasksResponse listResponse = new GetAllTasksResponse();
        listResponse.setTasks(taskResponses);
        return listResponse;
    }

    public GetAllTasksResponse getAllTasksByDateRange(LocalDateTime startDate, LocalDateTime endDate, Integer begin, Integer length) {
        String username = Methods.findActiveUsername();

        List<Task> tasks = taskRepository.findAllByDateRange(username, startDate, endDate, begin, length);
        List<TaskResponse> taskResponses = new ArrayList<>();

        for (Task task : tasks) {
            TaskResponse item = new TaskResponse();
            modelMapper.map(task, item);
            taskResponses.add(item);
        }

        GetAllTasksResponse listResponse = new GetAllTasksResponse();
        listResponse.setTasks(taskResponses);
        return listResponse;
    }

    public GetAllTasksResponse getAllTasksByDateRangeAndCategory(Integer categoryId, LocalDateTime startDate, LocalDateTime endDate, Integer begin, Integer length) {
        String username = Methods.findActiveUsername();

        List<Task> tasks = taskRepository.findAllByDateRangeAndCategory(username, categoryId, startDate, endDate, begin, length);
        List<TaskResponse> taskResponses = new ArrayList<>();

        for (Task task : tasks) {
            TaskResponse item = new TaskResponse();
            modelMapper.map(task, item);
            taskResponses.add(item);
        }

        GetAllTasksResponse listResponse = new GetAllTasksResponse();
        listResponse.setTasks(taskResponses);
        return listResponse;
    }

    public GetAllTasksResponse getAllCompletedTasks(Integer begin, Integer length) {
        String username = Methods.findActiveUsername();

        List<Task> tasks = taskRepository.findAllCompleted(username, begin, length);
        List<TaskResponse> taskResponses = new ArrayList<>();

        for (Task task : tasks) {
            TaskResponse item = new TaskResponse();
            modelMapper.map(task, item);
            taskResponses.add(item);
        }

        GetAllTasksResponse listResponse = new GetAllTasksResponse();
        listResponse.setTasks(taskResponses);
        return listResponse;
    }

    public GetAllTasksResponse getAllUncompletedTasks(Integer begin, Integer length) {
        String username = Methods.findActiveUsername();

        List<Task> tasks = taskRepository.findAllUncompleted(username, begin, length);
        List<TaskResponse> taskResponses = new ArrayList<>();

        for (Task task : tasks) {
            TaskResponse item = new TaskResponse();
            modelMapper.map(task, item);
            taskResponses.add(item);
        }

        GetAllTasksResponse listResponse = new GetAllTasksResponse();
        listResponse.setTasks(taskResponses);
        return listResponse;
    }

    public void updateTaskStatus(UpdateTaskStatusRequest updateTaskStatusRequest) {
        Integer id = updateTaskStatusRequest.getId();
        if (id == null || id == 0) {
            throw new OurRuntimeException("Enter Id");
        }
        Task task = taskRepository.findById(id).orElseThrow(() -> new OurRuntimeException("Task not found"));

        String owner = task.getOwner();
        String activeUsername = Methods.findActiveUsername();

        if (owner.equals(activeUsername)) {
            if (task.isCompleted()) {
                throw new OurRuntimeException("This task is already completed");
            }
            task.setCompleted(updateTaskStatusRequest.isCompleted());
            taskRepository.save(task);
        } else {
            throw new OurRuntimeException("You are not the owner of this Category");
        }
    }

    public void addTask(CreateTaskRequest createTaskRequest) {
        Optional<Task> existingTask = taskRepository.findByDescription(createTaskRequest.getDescription());

        String username = Methods.findActiveUsername();

        if(existingTask.isPresent()){
            throw new OurRuntimeException("This Task already exists");
        }

        Task task = new Task();
        task.setDescription(createTaskRequest.getDescription());
        task.setCategoryId(createTaskRequest.getCategoryId());
        task.setDueDays(createTaskRequest.getDueDays());
        task.setCreatedDate(LocalDateTime.now().withNano(0));
        task.setOwner(username);
        taskRepository.save(task);
    }

    public void updateTask(UpdateTaskRequest updateTaskRequest) {
        Integer id = updateTaskRequest.getId();
        if (id == null || id == 0) {
            throw new OurRuntimeException("Enter Id");
        }
        Task task = taskRepository.findById(id).orElseThrow(() -> new OurRuntimeException("Task not found"));

        String owner = task.getOwner();
        String activeUsername = Methods.findActiveUsername();

        if(owner.equals(activeUsername)){
            modelMapper.map(updateTaskRequest, task);
            taskRepository.save(task);
        } else {
            throw new OurRuntimeException("You are not the owner of this Task");
        }
    }

    public void delete(Integer id) {
        Optional<Task> optionalId = taskRepository.findById(id);

        if (optionalId.isPresent()){
            Task task = optionalId.get();
            String owner = task.getOwner();
            String activeUsername = Methods.findActiveUsername();

            if (owner.equals(activeUsername)){
                taskRepository.deleteById(id);
            } else {
                throw new OurRuntimeException("You are not owner of this Task");
            }
        } else{
            throw new OurRuntimeException("Task not found");
        }
    }

    public GetAllTasksResponse getAllByDescription(String description, Integer begin, Integer length) {
        String username = Methods.findActiveUsername();

        List<Task> tasks = taskRepository.findAllByDescription(username, description, begin, length);
        List<TaskResponse> taskResponses = new ArrayList<>();

        for (Task task : tasks) {
            TaskResponse item = new TaskResponse();
            modelMapper.map(task, item);
            taskResponses.add(item);
        }

        GetAllTasksResponse listResponse = new GetAllTasksResponse();
        listResponse.setTasks(taskResponses);
        return listResponse;
    }

    public GetAllTasksResponse getAllOverdueTasks(Integer begin, Integer length) {
        String username = Methods.findActiveUsername();

        List<Task> tasks = taskRepository.findAllOverdue(username, begin, length);
        List<TaskResponse> taskResponses = new ArrayList<>();

        for (Task task : tasks) {
            TaskResponse item = new TaskResponse();
            modelMapper.map(task, item);
            taskResponses.add(item);
        }

        GetAllTasksResponse listResponse = new GetAllTasksResponse();
        listResponse.setTasks(taskResponses);
        return listResponse;
    }
}
