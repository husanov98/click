package uz.mh.click.repository.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mh.click.domains.transactions.Card;

public interface CardRepository extends JpaRepository<Card,Long> {
}
