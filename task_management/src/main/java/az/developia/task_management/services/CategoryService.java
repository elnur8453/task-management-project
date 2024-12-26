package az.developia.task_management.services;

import az.developia.task_management.dtos.request.CreateCategoryRequest;
import az.developia.task_management.dtos.request.UpdateCategoryRequest;
import az.developia.task_management.dtos.response.CategoryResponse;
import az.developia.task_management.dtos.response.GetAllCategoriesResponse;
import az.developia.task_management.entities.Category;
import az.developia.task_management.exceptions.OurRuntimeException;
import az.developia.task_management.repositories.CategoryRepository;
import az.developia.task_management.utilities.Methods;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public GetAllCategoriesResponse getAll(Integer begin, Integer length) {
        String username = Methods.findActiveUsername();

        List<Category> categories = categoryRepository.findAll(username, begin, length);
        List<CategoryResponse> responses = new ArrayList<>();

        for (Category category : categories) {
            CategoryResponse item = new CategoryResponse();
            modelMapper.map(category, item);
            responses.add(item);
        }

        GetAllCategoriesResponse listResponse = new GetAllCategoriesResponse();
        listResponse.setCategories(responses);
        return listResponse;
    }

    public void add(CreateCategoryRequest createCategoryRequest) {
        Optional<Category> existingCategory = categoryRepository.findByName(createCategoryRequest.getName());

        String username = Methods.findActiveUsername();

        if (existingCategory.isPresent()){
            throw new OurRuntimeException("This Category already exists");
        }

        Category category = new Category();
        modelMapper.map(createCategoryRequest, category);
        category.setOwner(username);
        categoryRepository.save(category);
    }

    public void update(UpdateCategoryRequest updateCategoryRequest) {
        Integer id = updateCategoryRequest.getId();
        if (id == null || id == 0) {
            throw new OurRuntimeException("Enter Id");
        }
        Category category = categoryRepository.findById(id).orElseThrow(() -> new OurRuntimeException("Category not found"));

        String owner = category.getOwner();
        String activeUsername = Methods.findActiveUsername();

        if (owner.equals(activeUsername)) {
            modelMapper.map(updateCategoryRequest, category);
            categoryRepository.save(category);
        } else {
            throw new OurRuntimeException("You are not the owner of this Category");
        }
    }

    public void delete(Integer id) {
        Optional<Category> optionalId = categoryRepository.findById(id);

        if (optionalId.isPresent()) {
            Category category = optionalId.get();
            String owner = category.getOwner();
            String activeUsername = Methods.findActiveUsername();

            if (owner.equals(activeUsername)) {
                categoryRepository.deleteById(id);
            } else {
                throw new OurRuntimeException("You are not the owner of this Category");
            }
        } else {
            throw new OurRuntimeException("Category not found");
        }
    }
}
