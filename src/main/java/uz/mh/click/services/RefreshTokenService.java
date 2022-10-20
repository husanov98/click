package uz.mh.click.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import uz.mh.click.configs.security.UserDetails;
//import org.springframework.security.core.userdetails.UserDetails;
import uz.mh.click.utils.JwtUtils;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@Service
public class RefreshTokenService implements TokenService {

    private final JwtUtils jwtUtils;

    private final String secret;

    private final Integer amountToAdd;

    private final TemporalUnit timeUnit;

    public RefreshTokenService(@Lazy
                               JwtUtils jwtUtils,
                               @Value("${jwt.refresh.token.secret}") String secret,
                               @Value("${jwt.refresh.token.expiry.adding.amount}") Integer amountToAdd,
                               @Value("${jwt.refresh.token.expiry.time.unit}") String timeUnitName) {
        this.jwtUtils = jwtUtils;
        this.secret = secret;
        this.amountToAdd = amountToAdd;
        this.timeUnit = ChronoUnit.valueOf(timeUnitName);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return jwtUtils.jwt(
                userDetails.getUsername(),
                secret,
                amountToAdd,
                timeUnit
        );
    }


    @Override
    public boolean isValid(String token) {
        return jwtUtils.isValid(token, secret);
    }
}
