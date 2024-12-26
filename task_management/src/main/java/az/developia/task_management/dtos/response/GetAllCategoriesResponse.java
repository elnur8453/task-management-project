package az.developia.task_management.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCategoriesResponse {
    private List<CategoryResponse> categories;
}
