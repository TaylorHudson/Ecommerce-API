package br.com.compassuol.pb.challenge.msproducts.framework.adapters.in;

import br.com.compassuol.pb.challenge.msproducts.application.service.UserService;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.request.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static br.com.compassuol.pb.challenge.msproducts.utils.UserUtil.userRequestDefault;
import static br.com.compassuol.pb.challenge.msproducts.utils.UserUtil.userResponseDefault;
import static br.com.compassuol.pb.challenge.msproducts.utils.Utils.mapToString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {UserController.class})
class UserControllerTest {

    public static final String BASE_URL = "/users";
    public static final String ID_URL = "/users/1";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void createSuccess() throws Exception {
        var request = userRequestDefault();
        var responseDto = userResponseDefault();

        when(userService.create(any(UserRequest.class))).thenReturn(responseDto);

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
        var responseDto = userResponseDefault();

        when(userService.findById(anyLong())).thenReturn(responseDto);

        var result =
                mockMvc.perform(get(ID_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void updateSuccess() throws Exception {
        var request = userRequestDefault();
        var responseDto = userResponseDefault();

        when(userService.update(anyLong(), any(UserRequest.class))).thenReturn(responseDto);

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