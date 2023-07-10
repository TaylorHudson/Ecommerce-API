package br.com.compassuol.pb.challenge.msproducts.application.service;

import br.com.compassuol.pb.challenge.msproducts.domain.dto.request.CategoryRequest;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.response.CategoryResponse;
import br.com.compassuol.pb.challenge.msproducts.domain.model.CategoryModel;
import br.com.compassuol.pb.challenge.msproducts.framework.adapters.out.CategoryRepository;
import br.com.compassuol.pb.challenge.msproducts.framework.exception.CategoryAlreadyExistsException;
import br.com.compassuol.pb.challenge.msproducts.framework.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse create(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) throw new CategoryAlreadyExistsException(request.getName());

        var category = toCategory(request);
        var saved = categoryRepository.save(category);
        return toResponse(saved);
    }

    public List<CategoryResponse> findAll() {
        var categories = categoryRepository.findAll();
        return categories.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public CategoryResponse update(Long id, CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) throw new CategoryAlreadyExistsException(request.getName());
        categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id.toString()));
        var category = toCategory(request);
        category.setId(id);
        var updated = categoryRepository.save(category);
        return toResponse(updated);
    }

    public String deleteById(Long id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id.toString()));
        categoryRepository.delete(category);
        return "Category with id - " + id + " deleted";
    }

    private CategoryResponse toResponse(CategoryModel category) {
        return CategoryResponse.builder().id(category.getId()).name(category.getName()).build();
    }

    private CategoryModel toCategory(CategoryRequest request) {
        return CategoryModel.builder().name(request.getName()).build();
    }

}
