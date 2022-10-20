package uz.mh.click.repository.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mh.click.domains.transactions.Report;

public interface ReportRepository extends JpaRepository<Report,Long> {
}
