package br.com.compassuol.pb.challenge.msnotification.framework.exception;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorMessage {
    private LocalDateTime timestamp;
    private List<String> errors;
}
