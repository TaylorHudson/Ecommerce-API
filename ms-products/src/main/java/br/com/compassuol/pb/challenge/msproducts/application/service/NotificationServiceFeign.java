package br.com.compassuol.pb.challenge.msproducts.application.service;

import br.com.compassuol.pb.challenge.msproducts.domain.dto.request.EmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        value = "notification-service-feign",
        url = "http://localhost:8050"
)
public interface NotificationServiceFeign {
    @PostMapping("/email")
    String create(@RequestBody EmailRequest request);
}
