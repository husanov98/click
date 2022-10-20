package uz.mh.click.domains.transactions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import uz.mh.click.domains.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "payments")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "transactions")

public class Payment extends Auditable {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private float paymentAmount;

    private String cardNumber;
}
