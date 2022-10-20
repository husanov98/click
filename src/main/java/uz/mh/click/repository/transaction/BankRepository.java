package uz.mh.click.repository.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mh.click.domains.transactions.Bank;

public interface BankRepository extends JpaRepository<Bank,Long> {
}
