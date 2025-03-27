package cgb.transfert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cgb.transfert.entity.LotTransfer;

@Repository
public interface LotTransferRepository extends JpaRepository<LotTransfer, String>{

}
