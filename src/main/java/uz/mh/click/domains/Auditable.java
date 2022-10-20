package uz.mh.click.domains;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import org.springframework.boot.actuate.audit.listener.AuditListener;
import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.annotation.LastModifiedDate;


import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditListener.class)
public class Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "smallint default")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean deleted;

    @CreationTimestamp
    @CreatedDate
    @Column(nullable = false, columnDefinition = "timestamp default current_timestamp", unique = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


}
