package br.com.compassuol.pb.challenge.msproducts.framework.adapters.in;

import br.com.compassuol.pb.challenge.msproducts.application.service.CategoryService;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.request.CategoryRequest;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.response.CategoryResponse;
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

import static br.com.compassuol.pb.challenge.msproducts.utils.CategoryUtil.categoryRequestDefault;
import static br.com.compassuol.pb.challenge.msproducts.utils.CategoryUtil.categoryResponseDefault;
import static br.com.compassuol.pb.challenge.msproducts.utils.Utils.mapToString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {CategoryController.class})
class CategoryControllerTest {

    public static final String BASE_URL = "/categories";
    public static final String ID_URL = "/categories/1";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    void createSuccess() throws Exception {
        var request = categoryRequestDefault();
        var responseDto = categoryResponseDefault();

        when(categoryService.create(any(CategoryRequest.class))).thenReturn(responseDto);

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
    void findAllSuccess() throws Exception {
        var responses = new ArrayList<CategoryResponse>();
        responses.add(categoryResponseDefault());

        when(categoryService.findAll()).thenReturn(responses);

        var result =
                mockMvc.perform(get(BASE_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void updateSuccess() throws Exception {
        var request = categoryRequestDefault();
        var responseDto = categoryResponseDefault();

        when(categoryService.update(anyLong(), any(CategoryRequest.class))).thenReturn(responseDto);

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

    @Test
    void deleteSuccess() throws Exception {
        when(categoryService.deleteById(anyLong())).thenReturn(anyString());

        var result = mockMvc.perform(delete(ID_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

}