package uz.mh.click.controller.auth;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.mh.click.controller.ApiController;
import uz.mh.click.domains.auth.AuthUser;
import uz.mh.click.dtos.CreateProfileDto;
import uz.mh.click.dtos.JwtResponse;
import uz.mh.click.dtos.auth.LoginRequest;
import uz.mh.click.dtos.RefreshTokenRequest;
import uz.mh.click.response.ApiResponse;
import uz.mh.click.services.AuthUserService;

import javax.validation.Valid;

@RestController
public class AuthUserController extends ApiController<AuthUserService> {
    protected AuthUserController(AuthUserService service) {
        super(service);
    }

    @PostMapping(value = PATH + "/auth/login", produces = "application/json")
    public ApiResponse<JwtResponse> login(@RequestBody LoginRequest request) {
        return new ApiResponse<>(service.login(request));
    }

    @GetMapping(value = PATH + "/auth/refresh", produces = "application/json")
    public ApiResponse<JwtResponse> login(@RequestBody RefreshTokenRequest request) {
        return new ApiResponse<>(service.refreshToken(request));
    }

    @PostMapping(value = PATH + "/auth/register")
    public ApiResponse<AuthUser> register(@Valid @RequestBody LoginRequest request) {
        return new ApiResponse<>(service.register(request));
    }

    @PostMapping(value = PATH + "/auth/profile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<CreateProfileDto> fillProfile(@RequestBody CreateProfileDto dto, @RequestPart(name = "file") MultipartFile file) {
        return new ApiResponse<>(service.fillProfile(dto,file));
    }
}
