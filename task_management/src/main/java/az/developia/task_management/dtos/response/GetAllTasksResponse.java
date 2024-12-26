package az.developia.task_management.dtos.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetAllTasksResponse {
    private List<TaskResponse> tasks;
}
