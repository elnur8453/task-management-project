package az.developia.task_management.dtos.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskStatusRequest {
    private Integer id;
    private boolean completed;
}
