package cgb.transfert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cgb.transfert.entity.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
}