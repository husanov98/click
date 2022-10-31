package uz.mh.click.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mh.click.domains.auth.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
}
