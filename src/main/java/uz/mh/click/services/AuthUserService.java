package uz.mh.click.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.mh.click.domains.auth.AuthUser;
import uz.mh.click.dtos.JwtResponse;
import uz.mh.click.dtos.LoginRequest;
import uz.mh.click.dtos.RefreshTokenRequest;
import uz.mh.click.repository.auth.AuthUserRepository;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Service
public class AuthUserService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TokenService accessTokenService;

    private final TokenService refreshTokenService;


    public AuthUserService(AuthUserRepository authUserRepository,
                           PasswordEncoder passwordEncoder,
                           @Lazy AuthenticationManager authenticationManager,
                           @Qualifier("accessTokenService") TokenService accessTokenService,
                           @Qualifier("refreshTokenService") TokenService refreshTokenService) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.accessTokenService = accessTokenService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> exception = () -> new UsernameNotFoundException("Bad credentials");
        AuthUser authUser = authUserRepository.findByPhoneNumber(phoneNumber).orElseThrow(exception);
        return new uz.mh.click.configs.security.UserDetails(authUser);
    }

    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword()));
        uz.mh.click.configs.security.UserDetails userDetails = (uz.mh.click.configs.security.UserDetails) authentication.getPrincipal();
        String accessToken = accessTokenService.generateToken(userDetails);
        String refreshToken = refreshTokenService.generateToken(userDetails);
        AuthUser authUser = userDetails.authUser();
        authUser.setLastLoggedTime(LocalDateTime.now());
        authUserRepository.save(authUser);
        return new JwtResponse(accessToken, refreshToken, "Bearer");
    }

    public AuthUser register(LoginRequest request) {
        AuthUser authUser = new AuthUser();
        authUser.setCurrentPassword(passwordEncoder.encode(request.getPassword()));
        authUser.setPhoneNumber(request.getPhoneNumber());
        authUserRepository.save(authUser);
        return authUser;
    }

    public JwtResponse refreshToken(RefreshTokenRequest request) {
        String token = request.getRefreshToken();
        if (!accessTokenService.isValid(token)) {
            throw new RuntimeException("Token invalid");
        }
        String phoneNumber = refreshTokenService.getSubject(request.getRefreshToken());
        uz.mh.click.configs.security.UserDetails userDetails = (uz.mh.click.configs.security.UserDetails) loadUserByUsername(phoneNumber);
        String accessToken = accessTokenService.generateToken(userDetails);
        return new JwtResponse(accessToken, token, "Bearer");
    }
}

