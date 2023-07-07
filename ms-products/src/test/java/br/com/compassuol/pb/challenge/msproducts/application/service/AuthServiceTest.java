package br.com.compassuol.pb.challenge.msproducts.application.service;

import br.com.compassuol.pb.challenge.msproducts.domain.dto.request.LoginRequest;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.response.AuthResponse;
import br.com.compassuol.pb.challenge.msproducts.framework.adapters.out.UserRepository;
import br.com.compassuol.pb.challenge.msproducts.framework.security.CustomAuthenticationProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static br.com.compassuol.pb.challenge.msproducts.utils.UserUtil.userDefault;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private CustomAuthenticationProvider authProvider;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void loginSuccess() {
        var request = loginRequestDefault();
        var authenticated = new UsernamePasswordAuthenticationToken("jonh.doe@email.com", "johndoe123");
        String token = "generated_token";

        when(authProvider.authenticate(authenticated)).thenReturn(authenticated);
        when(jwtTokenProvider.generateToken(authenticated)).thenReturn(token);

        var response = authService.login(request);

        assertEquals(token, response.getToken());
        assertEquals("Bearer", response.getTokenType());

        verify(authProvider).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider).generateToken(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void validateSuccess() {
        var user = userDefault();
        var expectedResponse = authResponseDefault();
        var token = "token";
        var email = "john.doe@email.com";

        when(jwtTokenProvider.getEmail(anyString())).thenReturn(email);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        var response = authService.validate(token);

        assertEquals(expectedResponse.getToken(), response.getToken());
        assertEquals(expectedResponse.getEmail(), response.getEmail());

        verify(jwtTokenProvider).getEmail(anyString());
        verify(userRepository).findByEmail(anyString());
    }

    @Test
    void validateErrorBadCredentialsException() {
        var token = "token";
        var email = "john.doe@email.com";

        when(jwtTokenProvider.getEmail(anyString())).thenReturn(email);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(BadCredentialsException.class,() -> authService.validate(token));

        verify(jwtTokenProvider).getEmail(anyString());
        verify(userRepository).findByEmail(anyString());
    }

    private LoginRequest loginRequestDefault() {
        return LoginRequest.builder()
                .email("jonh.doe@email.com")
                .password("johndoe123")
                .build();
    }

    private AuthResponse authResponseDefault() {
        return AuthResponse.builder()
                .id(1L)
                .email("john.doe@email.com")
                .token("token")
                .build();
    }

}