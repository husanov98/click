package uz.mh.click.domains.transactions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import uz.mh.click.domains.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity(name = "reports")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "transactions")

public class Report extends Auditable {

    @Column(nullable = false)
    private float paymentAmount;

    @Column(nullable = false)
    private float comissionFee;

    @Column(nullable = false)
    private Timestamp transferedAt;

    @Column(nullable = false)
    private int numberOfPayment;

    @Column(nullable = false)
    private String senderFullName;

    @Column(nullable = false)
    private String recepientCardNumber;

    @Column(nullable = false)
    private String recepientFullName;

    @Column(nullable = false)
    private String fromCard;
}
