package uz.mh.click.configs.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.mh.click.configs.security.UserDetails;

import uz.mh.click.services.AuthUserService;
import uz.mh.click.services.TokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;

import static uz.mh.click.configs.security.SecurityConstant.WHITE_LIST;

public class JwtFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final AuthUserService authUserService;

    public JwtFilter(TokenService tokenService, AuthUserService authUserService) {
        this.tokenService = tokenService;
        this.authUserService = authUserService;
    }

    private final static Function<String, Boolean> isOpenUrl = (url) -> Arrays.stream(WHITE_LIST).anyMatch(s -> s.startsWith(url));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!isOpenUrl.apply(request.getRequestURI())) {
            try {
                String token = parseJwt(request);
                if (tokenService.isValid(token)) {
                    String phoneNumber = tokenService.getSubject(token);
                    UserDetails userDetails = (UserDetails) authUserService.loadUserByUsername(phoneNumber);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
