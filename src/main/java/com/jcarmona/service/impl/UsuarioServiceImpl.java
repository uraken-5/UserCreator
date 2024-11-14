package com.jcarmona.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jcarmona.config.exception.UserAlreadyExistsException;
import com.jcarmona.config.exception.UserEmailAlreadyExistsException;
import com.jcarmona.config.security.JwtToken;
import com.jcarmona.dto.UserDto;
import com.jcarmona.model.Phone;
import com.jcarmona.model.User;
import com.jcarmona.repository.UserRepository;
import com.jcarmona.service.interfaces.UsuarioService;
import com.jcarmona.utils.FormatDateNow;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UserRepository userRepository;
    private final JwtToken jwtToken;
    private final ModelMapper modelMapper;
    private final MessageSource messageSource;

    public UsuarioServiceImpl(UserRepository userRepository, ModelMapper modelMapper, JwtToken jwtToken, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.jwtToken = jwtToken;
        this.messageSource = messageSource;
    }

    /**
     * Guarda un usuario en la base de datos.
     *
     * @param userDTO El DTO del usuario.
     * @return Una respuesta con el usuario guardado.
     */
    @Override
    public Map<String, Object> saveUser(UserDto userDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(encoder.encode(userDTO.getPassword()));
        user.setCreated(FormatDateNow.getActualDate());
        user.setLastLogin(FormatDateNow.getActualDate());
        user.setActive(true);

        List<Phone> phoneList = userDTO.getPhones().stream()
                .map(phoneDto -> {
                    Phone phoneEntity = modelMapper.map(phoneDto, Phone.class);
                    phoneEntity.setUser(user);
                    return phoneEntity;
                }).collect(Collectors.toList());
        user.setPhones(phoneList);

        if (userEmailExists(user.getEmail())) {
            throw new UserEmailAlreadyExistsException(messageSource.getMessage("user.email.alreadyExists", null, null));
        }

        userRepository.save(user);
        
        Map<String, Object> response = getResponse(userRepository.findById(user.getId()).orElse(user));
        return response;
    }

    /**
     * Verifica si ya existe un usuario en la base de datos con el mismo correo electrónico.
     *
     * @param email El correo electrónico del usuario a verificar.
     * @return true si el usuario con el correo electrónico ya existe, false en caso contrario.
     */
    private boolean userEmailExists(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }
    
    private boolean userExists(UUID userId) {
    	Optional<User> userOptional = userRepository.findById(userId);
    	return (userOptional.isPresent()?true:false);
    }


    /**
     * Construye un mapa con la información del usuario recién guardado.
     *
     * @param user El objeto User que representa al usuario guardado.
     * @return Un mapa con los detalles del usuario, incluido el token JWT generado.
     */
    private Map<String, Object> getResponse(User user) {
        String token = jwtToken.generateJwtToken(user, jwtToken.getSecretKey());
        Map<String, Object> response = new HashMap<>();
        response.put(messageSource.getMessage("user.response.id", null, null), user.getId());
        response.put(messageSource.getMessage("user.response.created", null, null), LocalDateTime.now());
        response.put(messageSource.getMessage("user.response.lastLogin", null, null), LocalDateTime.now());
        response.put(messageSource.getMessage("user.response.token", null, null), token);
        response.put(messageSource.getMessage("user.response.active", null, null), true);
        response.put("email", user.getEmail());
        return response;
    }
}
