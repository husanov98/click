package uz.mh.click.domains.auth;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_profiles")
public class UserProfile{

    @Id
    @Column(unique = true,nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;
}
