package uz.mh.click.domains.transactions;

//import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import uz.mh.click.domains.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "payment_types")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Schema(name = "transactions")

public class PaymentType extends Auditable {
    @Column(nullable = false)
    private String name;

    private String pictureUrl;
    @OneToMany
    private List<Payment> payments;
}
