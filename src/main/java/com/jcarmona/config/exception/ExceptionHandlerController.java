package com.jcarmona.config.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController {

    private CustomError createCustomError(String message, HttpStatus status, String path) {
        CustomError error = new CustomError();
        error.setTimestamp(LocalDateTime.now());
        error.setCodigo(status.value());
        error.setDetail(message);
        error.setPath(path);
        return error;
    }

    /**
     * Maneja excepciones genéricas y devuelve una respuesta con código de estado 500 (Internal Server Error).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomError> handleException(Exception e, WebRequest request) {
    	CustomError error = createCustomError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * Maneja la excepción UserNotFoundException y devuelve una respuesta con código de estado 404 (Not Found).
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomError> handleUserNotFoundException(UserNotFoundException e, WebRequest request) {
        CustomError error = createCustomError(e.getMessage(), HttpStatus.NOT_FOUND, request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Maneja la excepción InvalidRequestException y devuelve una respuesta con código de estado 400 (Bad Request).
     */
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<CustomError> handleInvalidRequestException(InvalidRequestException e, WebRequest request) {
        CustomError error = createCustomError(e.getMessage(), HttpStatus.BAD_REQUEST, request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Maneja excepciones de tipo UserAlreadyExistsException y devuelve una respuesta con código de estado 409 (Conflict).
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<CustomError> handleUserAlreadyExistsException(UserAlreadyExistsException e, WebRequest request) {
        CustomError error = createCustomError(e.getMessage(), HttpStatus.CONFLICT, request.getDescription(false));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }


    /**
     * Maneja la excepción TokenErrorException y devuelve una respuesta con código de estado 403 (Forbidden).
     */
    @ExceptionHandler(TokenErrorException.class)
    public ResponseEntity<CustomError> handleTokenErrorException(TokenErrorException e, WebRequest request) {
        CustomError error = createCustomError(e.getMessage(), HttpStatus.FORBIDDEN, request.getDescription(false));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    /**
     * Maneja la excepción MethodArgumentNotValidException y devuelve una respuesta con código de estado 400 (Bad Request).
     * Además, convierte los errores de validación en una lista de objetos CustomError.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<CustomError>> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        List<CustomError> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            CustomError error = new CustomError();
            error.setTimestamp(LocalDateTime.now());
            error.setCodigo(HttpStatus.BAD_REQUEST.value());
            error.setDetail(fieldError.getDefaultMessage());
            error.setPath(request.getDescription(false));
            errors.add(error);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}