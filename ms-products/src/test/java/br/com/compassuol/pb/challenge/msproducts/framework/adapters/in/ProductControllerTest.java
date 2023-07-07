package br.com.compassuol.pb.challenge.msproducts.framework.adapters.in;

import br.com.compassuol.pb.challenge.msproducts.application.service.ProductService;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.request.ProductRequest;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.response.PageableResponse;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.response.ProductResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static br.com.compassuol.pb.challenge.msproducts.utils.ProductUtil.productRequestDefault;
import static br.com.compassuol.pb.challenge.msproducts.utils.ProductUtil.productResponseDefault;
import static br.com.compassuol.pb.challenge.msproducts.utils.Utils.mapToString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {ProductController.class})
class ProductControllerTest {

    public static final String BASE_URL = "/products";
    public static final String PAGE_URL = "/products?page=0&linesPerPage=10&direction=asc&orderBy=price";
    public static final String ID_URL = "/products/1";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void createSuccess() throws Exception {
        var request = productRequestDefault();
        var responseDto = productResponseDefault();

        when(productService.create(any(ProductRequest.class))).thenReturn(responseDto);

        String requestBody = mapToString(request);
        var result =
                mockMvc.perform(post(BASE_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void findByIdSuccess() throws Exception {
        var responseDto = productResponseDefault();

        when(productService.findById(anyLong())).thenReturn(responseDto);

        var result =
                mockMvc.perform(get(ID_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    @Test
    void findAllSuccess() throws Exception {
        var responses = new ArrayList<ProductResponse>();
        responses.add(productResponseDefault());

        var pageable = PageableResponse.<ProductResponse>builder()
                .content(responses)
                .page(0)
                .linesPerPage(10)
                .totalElements(1)
                .totalPages(1)
                .build();

        when(productService.findAll(0, 10, "asc", "price")).thenReturn(pageable);

        var result =
                mockMvc.perform(get(PAGE_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void deleteSuccess() throws Exception {
        when(productService.deleteById(anyLong())).thenReturn(anyString());

        var result = mockMvc.perform(delete(ID_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    @Test
    void updateSuccess() throws Exception {
        var request = productRequestDefault();
        var responseDto = productResponseDefault();

        when(productService.update(anyLong(), any(ProductRequest.class))).thenReturn(responseDto);

        String requestBody = mapToString(request);
        var result =
                mockMvc.perform(put(ID_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


}