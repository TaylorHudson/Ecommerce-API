package br.com.compassuol.pb.challenge.msproducts.framework.filters;

import br.com.compassuol.pb.challenge.msproducts.application.service.JwtTokenProvider;
import br.com.compassuol.pb.challenge.msproducts.framework.adapters.out.UserRepository;
import br.com.compassuol.pb.challenge.msproducts.framework.exception.ResourceNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromRequest(request);

        try {
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {

                String email = jwtTokenProvider.getEmail(token);

                var user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

                var authenticationToken = new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword(),
                        user.getRoles()
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                response.setHeader(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION));
            }
        } catch (Exception exception) {
            throw new BadCredentialsException(exception.getMessage());
        } finally {
            filterChain.doFilter(request, response);
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/oauth/token");
    }

    private String getTokenFromRequest(HttpServletRequest request) {

        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

}
