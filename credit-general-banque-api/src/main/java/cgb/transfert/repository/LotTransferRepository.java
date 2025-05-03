package cgb.transfert.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cgb.transfert.entity.LotTransfer;

@Repository
public interface LotTransferRepository extends JpaRepository<LotTransfer, Long>{

	boolean existsByRefLot(String refLot);
	Optional<LotTransfer> findByRefLot(String refLot);
}
