package uz.mh.click.domains.transactions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import uz.mh.click.domains.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity(name = "transfers")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "transactions")

public class Transfer extends Auditable {
    @Column(nullable = false, unique = true)
    private String phoneNumber;
    @NonNull
    private Timestamp operationTime;

    @Column(nullable = false)
    private float paymentAmount;

    @Column(nullable = false)
    private boolean isReceived = true;


}
