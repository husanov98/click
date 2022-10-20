package uz.mh.click.controller.auth;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uz.mh.click.controller.ApiController;
import uz.mh.click.domains.auth.AuthUser;
import uz.mh.click.dtos.JwtResponse;
import uz.mh.click.dtos.LoginRequest;
import uz.mh.click.dtos.RefreshTokenRequest;
import uz.mh.click.response.ApiResponse;
import uz.mh.click.services.AuthUserService;
import uz.mh.click.services.RefreshTokenService;

import javax.validation.Valid;

@RestController
public class AuthUserController extends ApiController<AuthUserService> {
    protected AuthUserController(AuthUserService service) {
        super(service);
    }

    @PostMapping(value = PATH + "/auth/login", produces = "application/json")
    public ApiResponse<JwtResponse> login(@RequestBody LoginRequest request) {
        return new ApiResponse<JwtResponse>(service.login(request));
    }

    @GetMapping(value = PATH + "/auth/refresh", produces = "application/json")
    public ApiResponse<JwtResponse> login(@RequestBody RefreshTokenRequest request) {
        return new ApiResponse<>(service.refreshToken(request));
    }

    @PostMapping(value = PATH + "/auth/register")
    public ApiResponse<AuthUser> register(@Valid @RequestBody LoginRequest request) {
        return new ApiResponse<>(service.register(request));
    }
}
