package uz.mh.click.dtos;

import lombok.*;
import uz.mh.click.domains.GenericParam;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreatePaymentDto {

    private String companyName;

    private List<GenericParam> params;
}
