package az.developia.task_management.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskRequest {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 20)
    private String description;

    @NotNull
    private Integer categoryId;

    @NotNull
    private Integer dueDays;
}
