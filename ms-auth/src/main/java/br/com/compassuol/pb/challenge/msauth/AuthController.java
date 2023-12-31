package br.com.compassuol.pb.challenge.msauth;

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

    private final AuthServiceFeign authServiceFeign;

    @PostMapping("/token")
    public ResponseEntity<?> auth(@RequestParam String token) {
        log.info("Trying to validate token {}", token);

        try {
            authServiceFeign.auth(token);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e) {
            log.info("Failed to validate token {}", token);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}