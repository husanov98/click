package uz.mh.click.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import uz.mh.click.configs.security.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.mh.click.domains.auth.AuthUser;
import uz.mh.click.dtos.CreateProfileDto;
import uz.mh.click.dtos.JwtResponse;
import uz.mh.click.dtos.LoginRequest;
import uz.mh.click.dtos.RefreshTokenRequest;
import uz.mh.click.repository.auth.AuthUserRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

import static com.twilio.example.ValidationExample.ACCOUNT_SID;
import static com.twilio.example.ValidationExample.AUTH_TOKEN;

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
        return new UserDetails(authUser);
    }

    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = accessTokenService.generateToken(userDetails);
        String refreshToken = refreshTokenService.generateToken(userDetails);
        AuthUser authUser = userDetails.authUser();
        authUser.setLastLoggedTime(LocalDateTime.now());
        authUserRepository.save(authUser);
        return new JwtResponse(accessToken, refreshToken, "Bearer");
    }

    public AuthUser register(LoginRequest request) {
        int code = new Random().nextInt(10000,99999);

//        Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
        AuthUser authUser = new AuthUser();
//        sendSmsToPhone(request.getPhoneNumber(), code);
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

    private void sendSmsToPhone(String phoneNumber, int code) {
//        Twilio.init(System.getenv("TWILIO_MESSAGE "));
        Message message = Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber("+998903501428"), String.valueOf(code)).create();
        System.out.println(message.getSid());
    }

    private AuthUser checkUserToBlock(Optional<AuthUser> optionalAuthUser) {
        return null;
    }

    public CreateProfileDto fillProfile(CreateProfileDto dto) {
        return null;
    }
}

