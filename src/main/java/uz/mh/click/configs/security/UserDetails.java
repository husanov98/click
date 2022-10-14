package uz.mh.click.configs.security;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import uz.mh.click.domains.auth.AuthUser;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

@Builder
public record UserDetails(AuthUser authUser) implements org.springframework.security.core.userdetails.UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (Objects.nonNull(authUser.getRoles()))
            authUser.getRoles().forEach(role -> {
                authorities.add(authority.apply(role.getAuthority()));
                if (Objects.nonNull(role.getPermissions()))
                    role.getPermissions().forEach(permission -> authorities.add(authority.apply(permission.getAuthority())));
            });
        return authorities;
    }

    @Override
    public String getPassword() {
        return authUser.getCurrentPassword();
    }

    @Override
    public String getUsername() {
        return authUser.getPhoneNumber();
    }

    @Override
    public boolean isAccountNonExpired() {
        return authUser.isActive(AuthUser.Status.ACCOUNT_EXPIRED);
    }

    @Override
    public boolean isAccountNonLocked() {
        return authUser.isActive(AuthUser.Status.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return authUser.isActive(AuthUser.Status.CREDENTIALS_EXPIRED);
    }

    @Override
    public boolean isEnabled() {
        return authUser.isDeleted();
    }

    private final static Function<String, SimpleGrantedAuthority> authority = SimpleGrantedAuthority::new;
}
