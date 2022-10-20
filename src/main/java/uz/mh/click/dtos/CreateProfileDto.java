package uz.mh.click.dtos;

import lombok.*;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateProfileDto {

    private String phoneNumber;

    private String firstname;

    private String lastname;

    private String middleName;

    private Date birthDate;
}
