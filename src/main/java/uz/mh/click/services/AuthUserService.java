package uz.mh.click.services;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import uz.mh.click.configs.security.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.mh.click.domains.auth.AuthUser;
import uz.mh.click.domains.auth.UserProfile;
import uz.mh.click.domains.fileStorage.Uploads;
import uz.mh.click.dtos.CreateProfileDto;
import uz.mh.click.dtos.JwtResponse;
import uz.mh.click.dtos.auth.LoginRequest;
import uz.mh.click.dtos.RefreshTokenRequest;
import uz.mh.click.repository.UploadsRepository;
import uz.mh.click.repository.auth.AuthUserRepository;
import uz.mh.click.repository.auth.UserProfileRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

@Service
public class AuthUserService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TokenService accessTokenService;

    private final TokenService refreshTokenService;

    private final UserProfileRepository profileRepository;

    private final UploadsRepository uploadsRepository;


    public AuthUserService(AuthUserRepository authUserRepository,
                           PasswordEncoder passwordEncoder,
                           @Lazy AuthenticationManager authenticationManager,
                           @Qualifier("accessTokenService") TokenService accessTokenService,
                           @Qualifier("refreshTokenService") TokenService refreshTokenService,
                           UserProfileRepository profileRepository,
                           UploadsRepository uploadsRepository) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.accessTokenService = accessTokenService;
        this.refreshTokenService = refreshTokenService;
        this.profileRepository = profileRepository;
        this.uploadsRepository = uploadsRepository;
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
//        String refreshToken = refreshTokenService.generateToken(userDetails);
        AuthUser authUser = userDetails.authUser();
        authUser.setLastLoggedTime(LocalDateTime.now());
        authUserRepository.save(authUser);
        return new JwtResponse(accessToken, "Bearer");
    }

    public AuthUser register(LoginRequest request) {
        int code = new Random().nextInt(10000, 99999);

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
        UserDetails userDetails = loadUserByUsername(phoneNumber);
        String accessToken = accessTokenService.generateToken(userDetails);
        return new JwtResponse(accessToken, "Bearer");
    }

    private void sendSmsToPhone(String phoneNumber, int code) {
//        Twilio.init(System.getenv("TWILIO_MESSAGE "));
        Message message = Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber("+998903501428"), String.valueOf(code)).create();
        System.out.println(message.getSid());
    }

    private AuthUser checkUserToBlock(Optional<AuthUser> optionalAuthUser) {
        return null;
    }

    public CreateProfileDto fillProfile(CreateProfileDto dto, MultipartFile file) {
        UserProfile profile = UserProfile.builder()
                .userId(getUserId())
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .middleName(dto.getMiddleName())
                .build();
        profileRepository.save(profile);
        Uploads picture = Uploads.childBuilder()
                .createdAt(LocalDateTime.now())
                .size(file.getSize())
                .generateName(file.getName())
                .mimeType(file.getContentType())
                .deleted(false)
                .type(Uploads.UploadsType.PROFILE_PICTURE)
                .originalName(file.getOriginalFilename())
                .build();
        uploadsRepository.save(picture);
        return dto;
    }

    private long getUserId() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String phoneNumber = userDetails.getUsername();
        Optional<AuthUser> authUser = authUserRepository.findByPhoneNumber(phoneNumber);
        return authUser.get().getId();
    }
}

