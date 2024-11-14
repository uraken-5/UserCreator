package com.jcarmona.config.exception;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CustomError {
    private LocalDateTime timestamp;
    private int codigo;
    private String detail;
    private String path;
}
