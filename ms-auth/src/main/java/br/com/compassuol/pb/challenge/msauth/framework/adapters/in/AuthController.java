package br.com.compassuol.pb.challenge.msauth.framework.adapters.in;

import br.com.compassuol.pb.challenge.msauth.application.service.AuthService;
import br.com.compassuol.pb.challenge.msauth.domain.dto.response.AuthResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<AuthResponse> auth(@RequestParam String token) {
        log.info("Trying to validate token {}", token);
        var response = authService.validate(token);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
