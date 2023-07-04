package br.com.compassuol.pb.challenge.msauth.framework.security;


import br.com.compassuol.pb.challenge.msauth.framework.adapters.out.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        var user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("No user registered with this details!"));

        if (!encoder.matches(password, user.getPassword()))
            throw new BadCredentialsException("Invalid credentials!");

        return new UsernamePasswordAuthenticationToken(email, password, user.getRoles());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
