package uz.mh.click.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mh.click.domains.fileStorage.Uploads;

public interface UploadsRepository extends JpaRepository<Uploads,Long> {
}
