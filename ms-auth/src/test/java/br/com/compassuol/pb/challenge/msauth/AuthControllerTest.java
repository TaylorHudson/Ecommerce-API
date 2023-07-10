package br.com.compassuol.pb.challenge.msauth;

import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = AuthController.class)
class AuthControllerTest {

    public static final String BASE_URL = "/oauth/token?token=token";

    @MockBean
    private AuthServiceFeign authServiceFeign;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void auth() throws Exception {
        var response = authResponseDefault();
        when(authServiceFeign.auth("validToken")).thenReturn(response);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private AuthResponse authResponseDefault() {
        return AuthResponse.builder()
                .id(1L)
                .token("token")
                .email("john.doe@gmail.com")
                .build();
    }

}