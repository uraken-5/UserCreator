package com.jcarmona.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jcarmona.model.Phone;

public interface PhoneRepository extends JpaRepository<Phone, Long> { }
