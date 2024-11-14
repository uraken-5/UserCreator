package com.jcarmona.service.impl;

import com.jcarmona.config.exception.UserEmailAlreadyExistsException;
import com.jcarmona.config.security.JwtToken;
import com.jcarmona.dto.PhoneDto;
import com.jcarmona.dto.UserDto;
import com.jcarmona.model.User;
import com.jcarmona.repository.UserRepository;
import com.jcarmona.service.interfaces.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceImplTest {

    private UsuarioService usuarioService;
    private UserRepository userRepository;
    private JwtToken jwtToken;
    private ModelMapper modelMapper;
    private MessageSource messageSource;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        jwtToken = mock(JwtToken.class);
        modelMapper = new ModelMapper();
        messageSource = mock(MessageSource.class);
        usuarioService = new UsuarioServiceImpl(userRepository, modelMapper, jwtToken, messageSource);
    }

    @Test
    void saveUser_ShouldCreateNewUser() {
        List<PhoneDto> phoneList = List.of(new PhoneDto(123456789, 1, "CL"));
        UserDto userDto = new UserDto("testUser", "test@mail.com", "Password1234", phoneList);

        User user = modelMapper.map(userDto, User.class);
        user.setId(UUID.randomUUID());
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);
        user.getPhones().forEach(phone -> phone.setUser(user));

   
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Sample message");

        var response = usuarioService.saveUser(userDto);


        assertNotNull(response, "La respuesta no debería ser nula.");
        assertEquals("test@mail.com", response.get("email"), "El email debería coincidir con el esperado.");
       
        verify(userRepository).save(any(User.class));
    }



    @Test
    void saveUser_ShouldThrowUserEmailAlreadyExistsException_WhenEmailExists() {
        List<PhoneDto> phoneList = List.of(new PhoneDto(123456789, 1, "CL"));
        UserDto userDto = new UserDto("testUser", "test@mail.com", "Password1234", phoneList);

        User existingUser = new User();
        existingUser.setEmail(userDto.getEmail());

        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(existingUser);

        assertThrows(UserEmailAlreadyExistsException.class, () -> usuarioService.saveUser(userDto));
    }


    @Test
    void saveUser_ShouldEncryptPassword() {
        List<PhoneDto> phoneList = List.of(new PhoneDto(123456789, 1, "CL"));
        UserDto userDto = new UserDto("testUser", "test@mail.com", "Password1234", phoneList);

        when(userRepository.findByEmail(anyString())).thenReturn(null);
        usuarioService.saveUser(userDto);

        verify(userRepository).save(argThat(user -> new BCryptPasswordEncoder().matches("Password1234", user.getPassword())));
    }

}
