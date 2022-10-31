package uz.mh.click.mappers;

import org.mapstruct.Mapper;
import uz.mh.click.domains.auth.AuthUser;
import uz.mh.click.dtos.auth.LoginRequest;

@Mapper(componentModel = "spring")
public interface AuthUserMapper {
    AuthUser fromRegisterDTO(LoginRequest dto);

    LoginRequest toDTO(AuthUser domain);
}
