package br.com.compassuol.pb.challenge.msproducts.application.service;

import io.jsonwebtoken.JwtParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import static br.com.compassuol.pb.challenge.msproducts.utils.UserUtil.userDefault;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    @Mock
    private JwtParser jwtParser;

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void generateToken() {
        var user = userDefault();

        var auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(user.getEmail());

        String token = jwtTokenProvider.generateToken(auth);

        assertNotNull(token);

        verify(auth).getName();
    }

}