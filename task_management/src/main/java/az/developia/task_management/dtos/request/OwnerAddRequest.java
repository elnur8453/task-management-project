package az.developia.task_management.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OwnerAddRequest {
    @NotNull
    @NotBlank
    @Size(min = 6, max = 20)
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 10, max = 10)
    private String phone;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 20)
    private String password;
}
