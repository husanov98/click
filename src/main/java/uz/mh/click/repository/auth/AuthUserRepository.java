package uz.mh.click.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mh.click.domains.auth.AuthUser;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByPhoneNumber(String phoneNumber);
}
