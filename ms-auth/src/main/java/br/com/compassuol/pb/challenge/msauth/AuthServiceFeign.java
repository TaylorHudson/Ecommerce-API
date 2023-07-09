package br.com.compassuol.pb.challenge.msauth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ms-products")
public interface AuthServiceFeign {
    @PostMapping("/oauth/validate")
    AuthResponse auth(@RequestParam String token);
}
