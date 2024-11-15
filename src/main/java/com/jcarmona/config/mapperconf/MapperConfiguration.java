package com.jcarmona.config.mapperconf;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jcarmona.dto.PhoneDto;
import com.jcarmona.dto.UserDto;
import com.jcarmona.model.Phone;
import com.jcarmona.model.User;

@Configuration
public class MapperConfiguration {

    @Bean
    public ModelMapper userMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configuring conversion DTO to entity
        TypeMap<UserDto, User> typeMapDto2Entity = modelMapper.createTypeMap(UserDto.class, User.class);
        typeMapDto2Entity.addMappings(mapper -> {
            mapper.map(UserDto::getName, User::setName);
            mapper.map(UserDto::getPassword, User::setPassword);
            mapper.map(UserDto::getEmail, User::setEmail);
            mapper.map(UserDto::getPhones, User::setPhones);
        });

        TypeMap<User, UserDto> typeMapEntity2Dto = modelMapper.createTypeMap(User.class, UserDto.class);
        typeMapEntity2Dto.addMappings(mapper -> {
            mapper.map(User::getName, UserDto::setName);
            mapper.map(User::getPassword, UserDto::setPassword);
            mapper.map(User::getEmail, UserDto::setEmail);
            mapper.map(User::getPhones, UserDto::setPhones);
        });

        TypeMap<PhoneDto, Phone> typeMapPhoneDto2Entity = modelMapper.createTypeMap(PhoneDto.class, Phone.class);
        typeMapPhoneDto2Entity.addMappings(mapper -> {
            mapper.map(PhoneDto::getNumber, Phone::setUser);
            mapper.map(PhoneDto::getCountryCode, Phone::setCountryCode);
            mapper.map(PhoneDto::getCityCode, Phone::setCityCode);
        });

        return modelMapper;
    }
}
