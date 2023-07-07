package br.com.compassuol.pb.challenge.msproducts.framework.adapters.in;

import br.com.compassuol.pb.challenge.msproducts.application.service.AuthService;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.request.LoginRequest;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.response.JwtTokenResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static br.com.compassuol.pb.challenge.msproducts.utils.Utils.mapToString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {LoginController.class})
class LoginControllerTest {

    public static final String BASE_URL = "/login";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    void loginSuccess() throws Exception {
        var request = loginRequestDefault();
        var responseDto = jwtTokenResponseDefault();

        when(authService.login(any(LoginRequest.class))).thenReturn(responseDto);

        String requestBody = mapToString(request);
        var result =
                mockMvc.perform(post(BASE_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    private LoginRequest loginRequestDefault() {
        return LoginRequest.builder()
                    .email("john.doe@gmail.com")
                    .password("johndoe123")
                    .build();
    }

    private JwtTokenResponse jwtTokenResponseDefault() {
        return JwtTokenResponse.builder()
                .token("token")
                .tokenType("Bearer")
                .build();
    }

}