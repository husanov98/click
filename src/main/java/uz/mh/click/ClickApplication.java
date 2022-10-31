package uz.mh.click;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.mh.click.domains.auth.AuthPermission;
import uz.mh.click.domains.auth.AuthRole;
import uz.mh.click.domains.auth.AuthUser;
import uz.mh.click.repository.auth.AuthUserRepository;

import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class ClickApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClickApplication.class, args);
    }

//    @Bean
    CommandLineRunner runner(PasswordEncoder passwordEncoder, AuthUserRepository repository) {
        return (args) -> {
            AuthRole managerRole = AuthRole.builder()
                    .code("MANAGER")
                    .name("Manager")
                    .build();

            AuthRole adminRole = AuthRole.builder()
                    .code("ADMIN")
                    .name("Admin")
                    .permissions(List.of(AuthPermission.builder().code("CREATE_MANAGER").name("Create Manager").build()))
                    .build();

            AuthRole employeeRole = AuthRole.builder()
                    .code("EMPLOYEE")
                    .name("Employee")
                    .build();

            AuthUser admin = AuthUser.builder()
                    .phoneNumber("admin")
                    .currentPassword(passwordEncoder.encode("123"))
                    .roles(List.of(adminRole, managerRole, employeeRole))
                    .build();

            AuthUser manager = AuthUser.builder()
                    .phoneNumber("manager")
                    .currentPassword(passwordEncoder.encode("123"))
                    .roles(List.of(managerRole, employeeRole))
                    .build();

            AuthUser user = AuthUser.builder()
                    .phoneNumber("user")
                    .currentPassword(passwordEncoder.encode("123"))
                    .roles(List.of(employeeRole))
                    .build();
            repository.saveAll(List.of(admin, manager, user));
        };
    }
}
