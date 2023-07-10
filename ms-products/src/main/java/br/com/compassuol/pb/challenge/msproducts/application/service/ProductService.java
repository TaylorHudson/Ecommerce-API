package br.com.compassuol.pb.challenge.msproducts.application.service;

import br.com.compassuol.pb.challenge.msproducts.domain.dto.request.ProductRequest;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.response.PageableResponse;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.response.ProductResponse;
import br.com.compassuol.pb.challenge.msproducts.domain.model.CategoryModel;
import br.com.compassuol.pb.challenge.msproducts.domain.model.ProductModel;
import br.com.compassuol.pb.challenge.msproducts.framework.adapters.out.CategoryRepository;
import br.com.compassuol.pb.challenge.msproducts.framework.adapters.out.ProductRepository;
import br.com.compassuol.pb.challenge.msproducts.framework.exception.InvalidPriceException;
import br.com.compassuol.pb.challenge.msproducts.framework.exception.ProductAlreadyExistsException;
import br.com.compassuol.pb.challenge.msproducts.framework.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Transactional
    public ProductResponse create(ProductRequest request) {
        if (productRepository.existsByName(request.getName())) throw new ProductAlreadyExistsException(request.getName());
        if (request.getPrice() <= 0) throw new InvalidPriceException();

        var productModel = createProductModel(request);
        productModel.setDate(LocalDateTime.now());
        var saved = productRepository.save(productModel);

        return createProductResponse(saved);
    }

    public ProductResponse findById(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id.toString()));

        return createProductResponse(product);
    }

    public PageableResponse<ProductResponse> findAll(int page, int linesPerPage, String direction, String orderBy) {
        var sort = direction.equalsIgnoreCase("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy).ascending();

        var pageable = PageRequest.of(page, linesPerPage, sort);
        var pageProduct = productRepository.findAll(pageable);
        var productList = pageProduct.getContent();

        var productResponse = productList.stream().map(this::createProductResponse).collect(Collectors.toList());

        return PageableResponse.<ProductResponse>builder()
                .content(productResponse)
                .page(pageProduct.getNumber())
                .linesPerPage(pageProduct.getSize())
                .totalElements(pageProduct.getTotalElements())
                .totalPages(pageProduct.getTotalPages())
                .build();
    }

    public String deleteById(Long id) {
        var product = productRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id.toString()));

        productRepository.delete(product);
        return "Product with id - " + id + "deleted";
    }

    public ProductResponse update(Long id, ProductRequest request) {
        if (productRepository.existsByName(request.getName())) throw new ProductAlreadyExistsException(request.getName());
        if (request.getPrice() <= 0) throw new InvalidPriceException();

        var oldProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id.toString()));

        var productModel = createProductModel(request);
        productModel.setDate(oldProduct.getDate());
        productModel.setId(id);
        var updated = productRepository.save(productModel);

        return createProductResponse(updated);
    }

    private ProductModel createProductModel(ProductRequest request) {
        var productModel = ProductModel.builder().request(request).build();
        productModel.setCategories(toCategory(request.getCategories()));
        return productModel;
    }

    private ProductResponse createProductResponse(ProductModel product) {
        var response = ProductResponse.builder().product(product).build();
        var categoriesId = toCategoriesId(product.getCategories());
        response.setCategories(categoriesId);
        return response;
    }

    private Set<Long> toCategoriesId(Set<CategoryModel> categories) {
        return categories.stream().map(CategoryModel::getId).collect(Collectors.toSet());
    }

    private Set<CategoryModel> toCategory(Set<Long> categoriesId) {
        var categories = new HashSet<CategoryModel>();

        categoriesId
                .forEach(id -> {
                    var category = categoryRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id.toString()));
                    categories.add(category);
                });
        return categories;
    }

}
