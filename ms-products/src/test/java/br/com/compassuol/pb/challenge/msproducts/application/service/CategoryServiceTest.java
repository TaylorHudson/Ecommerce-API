package br.com.compassuol.pb.challenge.msproducts.application.service;

import br.com.compassuol.pb.challenge.msproducts.domain.dto.response.CategoryResponse;
import br.com.compassuol.pb.challenge.msproducts.domain.model.CategoryModel;
import br.com.compassuol.pb.challenge.msproducts.framework.adapters.out.CategoryRepository;
import br.com.compassuol.pb.challenge.msproducts.framework.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static br.com.compassuol.pb.challenge.msproducts.utils.CategoryUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void createSuccess() {
        var category = categoryDefault();
        var request = categoryRequestDefault();
        var expectedResponse = categoryResponseDefault();

        when(categoryRepository.save(any(CategoryModel.class))).thenReturn(category);

        var response = categoryService.create(request);

        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getName(), response.getName());

        verify(categoryRepository).save(any(CategoryModel.class));
    }

    @Test
    void findAll() {
        var categories = new ArrayList<CategoryModel>();
        categories.add(categoryDefault());
        var expectedCategories = new ArrayList<CategoryResponse>();
        expectedCategories.add(categoryResponseDefault());

        when(categoryRepository.findAll()).thenReturn(categories);

        var response = categoryService.findAll();

        assertEquals(expectedCategories.get(0).getId(), response.get(0).getId());
        assertEquals(expectedCategories.get(0).getName(), response.get(0).getName());

        verify(categoryRepository).findAll();
    }

    @Test
    void updateSuccess() {
        var category = categoryDefault();
        var request = categoryRequestDefault();
        var expectedResponse = categoryResponseDefault();

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(CategoryModel.class))).thenReturn(category);

        var response = categoryService.update(1L, request);

        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getName(), response.getName());

        verify(categoryRepository).findById(anyLong());
        verify(categoryRepository).save(any(CategoryModel.class));
    }

    @Test
    void updateErrorResourceNotFoundException() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.update(1L, categoryRequestDefault()));

        verify(categoryRepository).findById(anyLong());
        verify(categoryRepository, times(0)).save(any(CategoryModel.class));
    }

    @Test
    void deleteByIdSuccess() {
        var category = categoryDefault();

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        assertDoesNotThrow(() -> categoryService.deleteById(anyLong()));

        verify(categoryRepository).findById(anyLong());
        verify(categoryRepository).delete(any(CategoryModel.class));
    }

    @Test
    void deleteByIdErrorResourceNotFoundException() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteById(anyLong()));

        verify(categoryRepository).findById(anyLong());
        verify(categoryRepository, times(0)).delete(any(CategoryModel.class));
    }

}