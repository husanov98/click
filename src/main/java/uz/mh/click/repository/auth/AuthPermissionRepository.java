package uz.mh.click.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mh.click.domains.auth.AuthPermission;

public interface AuthPermissionRepository extends JpaRepository<AuthPermission,Long> {
}
