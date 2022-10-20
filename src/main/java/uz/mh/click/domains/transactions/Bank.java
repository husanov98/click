package uz.mh.click.domains.transactions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import uz.mh.click.domains.Auditable;

import javax.persistence.Entity;

@Entity(name = "banks")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "transactions")

public class Bank extends Auditable {

    private String name;
}
