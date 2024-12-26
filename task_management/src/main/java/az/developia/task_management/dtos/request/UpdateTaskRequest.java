package az.developia.task_management.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskRequest {
    private Integer id;
    private String description;
    private Integer categoryId;
    private Integer dueDays;
}
