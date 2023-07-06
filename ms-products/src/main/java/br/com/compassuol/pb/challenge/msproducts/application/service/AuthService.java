package br.com.compassuol.pb.challenge.msproducts.application.service;

import br.com.compassuol.pb.challenge.msproducts.domain.dto.request.LoginRequest;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.response.AuthResponse;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.response.JwtTokenResponse;
import br.com.compassuol.pb.challenge.msproducts.framework.adapters.out.UserRepository;
import br.com.compassuol.pb.challenge.msproducts.framework.security.CustomAuthenticationProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final CustomAuthenticationProvider authProvider;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    public JwtTokenResponse login(LoginRequest request) {
        var auth = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        var authenticated = authProvider.authenticate(auth);

        SecurityContextHolder.getContext().setAuthentication(authenticated);

        var token = jwtTokenProvider.generateToken(authenticated);
        return JwtTokenResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .build();
    }

    public AuthResponse validate(String token) {
        String email = jwtTokenProvider.getEmail(token);
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid Credentials"));
        return AuthResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .token(token)
                .build();
    }

}
