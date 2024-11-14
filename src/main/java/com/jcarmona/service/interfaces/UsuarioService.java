package com.jcarmona.service.interfaces;

import java.util.Map;

import com.jcarmona.dto.UserDto;

@FunctionalInterface
public interface UsuarioService {
    Map<String, Object> saveUser(UserDto userDTO);
}
