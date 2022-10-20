package uz.mh.click.domains.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "auth_role")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "auth")

public class AuthRole implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<AuthPermission> permissions;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.code;
    }
}
