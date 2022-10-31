package uz.mh.click.dtos;

import lombok.*;
import org.springdoc.api.annotations.ParameterObject;
import uz.mh.click.enums.Region;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ParameterObject
public class CreateProfileDto {

    private String firstname;

    private String lastname;

    private String middleName;

    private Date birthDate;

    private Region region;
}
