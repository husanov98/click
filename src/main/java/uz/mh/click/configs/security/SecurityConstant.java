package uz.mh.click.configs.security;

public class SecurityConstant {
    public static final String[] WHITE_LIST = {
//            "/api/v1/auth/payment",
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/activate**",
            "/swagger-ui/**",
            "/api-docs/**"
    };
}
