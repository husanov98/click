package uz.mh.click.domains.fileStorage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.mh.click.domains.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
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

    private UploadsType type = UploadsType.UPLOADED_FILE;


    public enum UploadsType {
        UPLOADED_FILE,
        PROFILE_PICTURE,
        TZ,
        WORK_SPACE_DOC
    }
}
