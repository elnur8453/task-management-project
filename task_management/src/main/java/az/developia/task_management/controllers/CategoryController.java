package az.developia.task_management.controllers;

import az.developia.task_management.dtos.request.CreateCategoryRequest;
import az.developia.task_management.dtos.request.UpdateCategoryRequest;
import az.developia.task_management.dtos.response.GetAllCategoriesResponse;
import az.developia.task_management.services.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {
    private CategoryService categoryService;

    @GetMapping
    @PreAuthorize(value = "hasAuthority('ROLE_GET_CATEGORY')")
    public GetAllCategoriesResponse getAllCategories(
            @RequestParam(name = "begin", required = false, defaultValue = "0") Integer begin,
            @RequestParam(name = "length", required = false, defaultValue = "10") Integer length
    ) {
        return categoryService.getAll(begin, length);
    }

    @PostMapping
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADD_CATEGORY')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
        categoryService.add(createCategoryRequest);
    }

    @PutMapping
    @PreAuthorize(value = "hasAnyAuthority('ROLE_UPDATE_CATEGORY')")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateCategory(@RequestBody UpdateCategoryRequest updateCategoryRequest){
        categoryService.update(updateCategoryRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_DELETE_CATEGORY')")
    public void deleteById(@PathVariable Integer id) {
        categoryService.delete(id);
    }

}
