package br.com.academy.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BadRequestExceptionDetails {

    private String title;
    private String details;
    private String developerMessage;
    private int status;
    private LocalDateTime timestamp;

}
