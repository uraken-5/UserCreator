package com.jcarmona.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jcarmona.config.security.JwtToken;
import com.jcarmona.dto.UserDto;
import com.jcarmona.service.interfaces.LoginService;
import com.jcarmona.service.interfaces.UsuarioService;

import java.util.Map;

@RestController
@ControllerAdvice
public class UsuarioController {

    private final UsuarioService usuarioService;
 

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
  
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody @Validated UserDto userDTO) {
        Map<String, Object> response = usuarioService.saveUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
