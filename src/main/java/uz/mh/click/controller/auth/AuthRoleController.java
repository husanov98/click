package uz.mh.click.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.mh.click.services.AuthRoleService;

@RestController
@RequiredArgsConstructor
public class AuthRoleController {

    private final AuthRoleService authRoleService;
}
