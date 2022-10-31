package uz.mh.click.domains.transactions;

//import io.swagger.v3.oas.annotations.media.Schema;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.*;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import uz.mh.click.domains.Auditable;
import uz.mh.click.domains.GenericParam;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "payments")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Schema(name = "transactions")
@Table(name = "payments")
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
//@Builder
public class Payment extends Auditable {

    @Column(nullable = false)
    private String companyName;


    private String fromCardNumber;

    @Type(type = "com.vladmihalcea.hibernate.type.array.ListArrayType",
    parameters = {
            @Parameter(
                    name = ListArrayType.SQL_ARRAY_TYPE,
                    value = "generic_param"
            )
    })
    @Column(
            name = "generic_params",
            columnDefinition = "generic_param[]"
    )
    private List<GenericParam> genericParams;

    @Builder(builderMethodName = "childBuilder")
    public Payment(Long id, boolean deleted, LocalDateTime createdAt, LocalDateTime updatedAt, String companyName, String fromCardNumber, List<GenericParam> genericParams) {
        super(id, deleted, createdAt, updatedAt);
        this.companyName = companyName;
        this.fromCardNumber = fromCardNumber;
        this.genericParams = genericParams;
    }
}
