package br.com.compassuol.pb.challenge.msproducts.application.service;

import br.com.compassuol.pb.challenge.msproducts.domain.dto.response.ProductResponse;
import br.com.compassuol.pb.challenge.msproducts.domain.model.ProductModel;
import br.com.compassuol.pb.challenge.msproducts.framework.adapters.out.CategoryRepository;
import br.com.compassuol.pb.challenge.msproducts.framework.adapters.out.ProductRepository;
import br.com.compassuol.pb.challenge.msproducts.framework.exception.InvalidPriceException;
import br.com.compassuol.pb.challenge.msproducts.framework.exception.ProductAlreadyExistsException;
import br.com.compassuol.pb.challenge.msproducts.framework.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Optional;

import static br.com.compassuol.pb.challenge.msproducts.utils.CategoryUtil.categoryDefault;
import static br.com.compassuol.pb.challenge.msproducts.utils.ProductUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createSuccess() {
        var product = productDefault();
        var category = categoryDefault();
        var request = productRequestDefault();
        var expectedResponse = productResponseDefault();

        when(productRepository.existsByName(anyString())).thenReturn(false);
        when(productRepository.save(any(ProductModel.class))).thenReturn(product);
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        var response = productService.create(request);

        assertEquals(expectedResponse.getName(), response.getName());
        assertEquals(expectedResponse.getDescription(), response.getDescription());
        assertEquals(expectedResponse.getPrice(), response.getPrice());

        verify(productRepository).existsByName(anyString());
        verify(productRepository).save(any(ProductModel.class));
        verify(categoryRepository).findById(anyLong());
    }

    @Test
    void createErrorInvalidPriceException() {
        var request = productRequestDefault();
        request.setPrice(0);

        when(productRepository.existsByName(anyString())).thenReturn(false);

        assertThrows(InvalidPriceException.class, () ->  productService.create(request));

        verify(productRepository).existsByName(anyString());
        verify(productRepository, times(0)).save(any(ProductModel.class));
        verify(categoryRepository, times(0)).findById(anyLong());
    }

    @Test
    void createErrorProductAlreadyExistsException() {
        var request = productRequestDefault();

        when(productRepository.existsByName(anyString())).thenReturn(true);

        assertThrows(ProductAlreadyExistsException.class, () ->  productService.create(request));

        verify(productRepository).existsByName(anyString());
        verify(productRepository, times(0)).save(any(ProductModel.class));
        verify(categoryRepository, times(0)).findById(anyLong());
    }


    @Test
    void findByIdSuccess() {
        var product = productDefault();
        var expectedResponse = productResponseDefault();

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        var response = productService.findById(anyLong());

        assertEquals(expectedResponse.getName(), response.getName());
        assertEquals(expectedResponse.getPrice(), response.getPrice());
        assertEquals(expectedResponse.getDescription(), response.getDescription());
        assertEquals(1, response.getCategories().size());

        verify(productRepository).findById(anyLong());
    }

    @Test
    void findByIdErrorResourceNotFoundException() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.findById(anyLong()));

        verify(productRepository).findById(anyLong());
    }

    @Test
    void findAllSuccess() {
        int page = 0;
        int linesPerPage = 10;
        String orderBy = "price";
        var sort = Sort.by(orderBy).ascending();
        var pageable = PageRequest.of(page, linesPerPage, sort);

        var products = new ArrayList<ProductModel>();
        products.add(productDefault());
        var expectedProducts = new ArrayList<ProductResponse>();
        expectedProducts.add(productResponseDefault());

        var pageProduct = new PageImpl<>(products, pageable, products.size());

        when(productRepository.findAll(any(Pageable.class))).thenReturn(pageProduct);

        var response = productService.findAll(page, linesPerPage, "asc", orderBy);

        var content = response.getContent();

        assertEquals(expectedProducts.get(0).getName(), content.get(0).getName());
        assertEquals(expectedProducts.get(0).getDescription(), content.get(0).getDescription());
        assertEquals(expectedProducts.size(), content.size());

        verify(productRepository).findAll(any(Pageable.class));
    }

    @Test
    void deleteByIdSuccess() {
        var product = productDefault();

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        assertDoesNotThrow(() -> productService.deleteById(anyLong()));

        verify(productRepository).findById(anyLong());
        verify(productRepository).delete(product);
    }

    @Test
    void deleteByIdErrorResourceNotFoundException() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.deleteById(anyLong()));

        verify(productRepository).findById(anyLong());
        verify(productRepository, times(0)).delete(any(ProductModel.class));
    }

    @Test
    void updateSuccess() {
        var product = productDefault();
        var category = categoryDefault();
        var request = productRequestDefault();
        var expectedResponse = productResponseDefault();

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(productRepository.save(any(ProductModel.class))).thenReturn(product);

        var response = productService.update(1L, request);

        assertEquals(expectedResponse.getName(), response.getName());
        assertEquals(expectedResponse.getDescription(), response.getDescription());
        assertEquals(expectedResponse.getPrice(), response.getPrice());

        verify(productRepository).findById(anyLong());
        verify(categoryRepository).findById(anyLong());
        verify(productRepository).save(any(ProductModel.class));
    }

    @Test
    void updateErrorResourceNotFoundException() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.update(1L, productRequestDefault()));

        verify(productRepository).findById(anyLong());
        verify(categoryRepository, times(0)).findById(anyLong());
        verify(productRepository, times(0)).save(any(ProductModel.class));
    }

    @Test
    void updateErrorProductAlreadyExistsException() {
        when(productRepository.existsByName(anyString())).thenReturn(true);

        assertThrows(ProductAlreadyExistsException.class, () -> productService.update(1L, productRequestDefault()));

        verify(productRepository).existsByName(anyString());
        verify(productRepository, times(0)).findById(anyLong());
        verify(categoryRepository, times(0)).findById(anyLong());
        verify(productRepository, times(0)).save(any(ProductModel.class));
    }

    @Test
    void updateErrorInvalidPriceException() {
        var request = productRequestDefault();
        request.setPrice(0);

        when(productRepository.existsByName(anyString())).thenReturn(false);

        assertThrows(InvalidPriceException.class, () -> productService.update(1L, request));

        verify(productRepository).existsByName(anyString());
        verify(productRepository, times(0)).findById(anyLong());
        verify(categoryRepository, times(0)).findById(anyLong());
        verify(productRepository, times(0)).save(any(ProductModel.class));
    }

}