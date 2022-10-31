package uz.mh.click.domains.auth;

//import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import uz.mh.click.domains.Auditable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Setter
@Getter
@AllArgsConstructor
//@Schema(name = "auth")

@Entity(name = "auth_user")
@Builder
public class AuthUser extends Auditable {

    @Column(nullable = false, unique = true)
    private String phoneNumber;
    private String firstname;

    private String lastname;

    private String middleName;

    private LocalDate birthDate;

    private int loginTryCount;

    @Column(nullable = false)
    private String currentPassword;

    private String newPassword;

    private LocalDateTime lastLoggedTime;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.NON_ACTIVE;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "auth_user_auth_role",
            joinColumns = @JoinColumn(name = "auth_user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "auth_role_id", referencedColumnName = "id"))
    private Collection<AuthRole> roles;

    @Builder(builderMethodName = "childBuilder")
    public AuthUser(Long id, boolean deleted, LocalDateTime createdAt, LocalDateTime updatedAt, String phoneNumber, String firstname, String lastname, String middleName, LocalDate birthDate, String currentPassword, String newPassword, LocalDateTime lastLoggedTime, byte loginTryCount, Status status, Collection<AuthRole> roles) {
        super(id, deleted, createdAt, updatedAt);
        this.phoneNumber = phoneNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.lastLoggedTime = lastLoggedTime;
        this.loginTryCount = loginTryCount;
        this.status = status;
        this.roles = roles;
    }

    public AuthUser() {
    }

    public enum Status {
        ACTIVE, NON_ACTIVE, ACCOUNT_EXPIRED, CREDENTIALS_EXPIRED;
    }

    public boolean isActive(Status s) {
        return !this.equals(status);
    }
}
