package uz.mh.click.domains.auth;

import lombok.*;
import uz.mh.click.domains.Auditable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AuthUser extends Auditable {

    @Column(nullable = false,unique = true)
    private String phoneNumber;
    private String firstname;

    private String lastname;

    private String middleName;

    private LocalDate birthDate;

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
    public AuthUser(Long id, boolean deleted, LocalDateTime createdAt, Long createdBy, LocalDateTime updatedAt, Long updatedBy, String firstname, String lastname, LocalDate birthDate, String password, LocalDateTime lastLoggedTime, Status status, Collection<AuthRole> roles) {
        super(id, deleted, createdAt, createdBy, updatedAt, updatedBy);
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.currentPassword = password;
        this.lastLoggedTime = lastLoggedTime;
        this.status = status;
        this.roles = roles;
    }

    public enum Status {
        ACTIVE, NON_ACTIVE,ACCOUNT_EXPIRED,CREDENTIALS_EXPIRED;
    }

    public boolean isActive(Status s) {
        return !this.equals(status);
    }
}
