package com.jcarmona.service.interfaces;


import java.util.Map;

@FunctionalInterface
public interface LoginService {
    public Map<String, Object> loginUser(String token);
}
