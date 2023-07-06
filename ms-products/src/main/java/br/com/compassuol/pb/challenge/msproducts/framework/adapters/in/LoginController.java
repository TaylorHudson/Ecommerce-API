package br.com.compassuol.pb.challenge.msproducts.framework.adapters.in;

import br.com.compassuol.pb.challenge.msproducts.application.service.AuthService;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.request.LoginRequest;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.response.JwtTokenResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<JwtTokenResponse> login(@RequestBody LoginRequest request) {
        var response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
