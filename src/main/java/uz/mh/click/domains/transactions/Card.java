package uz.mh.click.domains.transactions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import uz.mh.click.domains.Auditable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "cards")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "transactions")

public class Card extends Auditable {

    @Column(nullable = false, length = 16)
    private String cardNumber;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    private double balance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CardType cardType;
    @ManyToOne
    private Bank bank;

    private boolean isBlocked;

    private boolean isBalanceSeeable;

    private boolean isCardNumberSeeable;

    public enum CardType {
        UZ_CARD,
        HUMO;
    }
}
