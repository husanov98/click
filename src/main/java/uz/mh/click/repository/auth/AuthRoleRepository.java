package uz.mh.click.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mh.click.domains.auth.AuthRole;

public interface AuthRoleRepository extends JpaRepository<AuthRole,Long> {
}
