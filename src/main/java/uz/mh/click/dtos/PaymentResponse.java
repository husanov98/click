package uz.mh.click.dtos;

import lombok.*;
import uz.mh.click.domains.GenericParam;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {

    private String companyName;

    private List<GenericParam> params;
}
