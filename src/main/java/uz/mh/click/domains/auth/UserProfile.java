package uz.mh.click.domains.auth;

//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import uz.mh.click.domains.fileStorage.Uploads;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_profiles")
//@Schema(name = "auth")

public class UserProfile{

    @Id
    @Column(unique = true,nullable = false)
    private Long userId;

    private String firstname;

    private String lastname;

    private String middleName;

    @OneToOne
    private Uploads picture;
}
