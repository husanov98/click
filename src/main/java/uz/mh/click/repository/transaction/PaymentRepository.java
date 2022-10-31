package uz.mh.click.repository.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mh.click.domains.transactions.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
