package uz.mh.click.domains.fileStorage;

//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import uz.mh.click.domains.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Entity


public class Uploads extends Auditable {

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String generateName;

    @Column(nullable = false)
    private String mimeType;

    @Column(nullable = false)
    private String path;

    private long size;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UploadsType type = UploadsType.UPLOADED_FILE;

    public Uploads() {
    }

    @Builder(builderMethodName = "childBuilder")
    public Uploads(Long id, boolean deleted, LocalDateTime createdAt, LocalDateTime updatedAt, String originalName, String generateName, String mimeType, String path, long size, UploadsType type) {
        super(id, deleted, createdAt, updatedAt);
        this.originalName = originalName;
        this.generateName = generateName;
        this.mimeType = mimeType;
        this.path = path;
        this.size = size;
        this.type = type;
    }

    public enum UploadsType {
        UPLOADED_FILE,
        PROFILE_PICTURE,
        TZ,
        WORK_SPACE_DOC
    }
}
