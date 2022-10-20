package uz.mh.click.services;

import uz.mh.click.configs.security.UserDetails;

public interface TokenService {

    String generateToken(UserDetails userDetails);

    boolean isValid(String token);

    default String getSubject(String token) {
        return null;
    }
}
