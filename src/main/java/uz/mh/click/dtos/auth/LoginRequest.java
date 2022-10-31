package uz.mh.click.dtos.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor

public class LoginRequest {
    @NotNull(message = "phoneNumber can not be null")
    private String phoneNumber;
    @NotNull(message = "Password can not be null")
    private String password;

}
