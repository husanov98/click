package uz.mh.click.domains.transactions;

import lombok.*;
import uz.mh.click.domains.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Entity(name = "cards")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card extends Auditable {

    @Column(nullable = false, length = 16)
    private String cardNumber;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    private double balance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    public enum CardType {
        UZ_CARD,
        HUMO;
    }
}
