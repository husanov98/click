package uz.mh.click.domains.transactions;

//import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import uz.mh.click.domains.Auditable;
import uz.mh.click.domains.GenericParam;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "payments")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Schema(name = "transactions")

public class Payment extends Auditable {
    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private float paymentAmount;

    private String fromCardNumber;
    @OneToMany
    private List<GenericParam> genericParams;

}
