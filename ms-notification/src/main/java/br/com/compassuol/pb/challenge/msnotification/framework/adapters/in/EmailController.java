package br.com.compassuol.pb.challenge.msnotification.framework.adapters.in;

import br.com.compassuol.pb.challenge.msnotification.application.service.EmailService;
import br.com.compassuol.pb.challenge.msnotification.domain.dto.request.EmailRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@AllArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody EmailRequest request) {
        var response = emailService.create(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
