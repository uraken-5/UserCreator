package com.jcarmona.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.GeneratedValue;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime created;
    private LocalDateTime lastLogin;
    private boolean isActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Phone> phones;
    

}
