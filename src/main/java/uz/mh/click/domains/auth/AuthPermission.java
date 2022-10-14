package uz.mh.click.domains.auth;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "auth_permission")
public class AuthPermission implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    private String name;

    @Override
    public String getAuthority() {
        return this.code;
    }
}
