package uz.mh.click.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

@Component
public class JwtUtils {

    public static final SignatureAlgorithm algorithm = SignatureAlgorithm.HS512;

    public boolean isValid(String token, String secret) {
        String subject = getSubject(token, secret);
        Date expiration = getClaim(token, Claims::getExpiration, secret);
        return (Objects.nonNull(subject) && !isTokenExpired(expiration));
    }
    private boolean isTokenExpired(Date expiration) {
        Instant now = Instant.now(Clock.systemDefaultZone());
        Instant instant = expiration.toInstant();
        boolean after = now.isAfter(instant);
        return after;
    }

    public String getSubject(String token, String secret) {
        return getClaim(token, Claims::getSubject, secret);
    }


    public <T> T getClaim(String token, Function<Claims, T> function, String secret) {
        Jws<Claims> claimsJws = jwtClaims(token, secret);
        Claims claims = claimsJws.getBody();
        return function.apply(claims);
    }

    private Jws<Claims> jwtClaims(@NonNull final String token, @NonNull final String secret) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String jwt(@NonNull final String subject,
                      @NonNull final String secret,
                      int amountToAdd, TemporalUnit unit) {
        return jwtBuilder(subject, secret, amountToAdd, unit).compact();
    }

    private JwtBuilder jwtBuilder(@NonNull final String subject,
                                  @NonNull final String secret,
                                  int amountToAdd, TemporalUnit unit) {
        Instant now = Instant.now(Clock.systemDefaultZone());
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(amountToAdd, unit)))
                .signWith(getKey(),algorithm);
    }
    private SecretKey getKey(){
        return Keys.secretKeyFor(algorithm);
    }
}
