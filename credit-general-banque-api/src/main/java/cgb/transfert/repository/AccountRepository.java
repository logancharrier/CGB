package cgb.transfert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cgb.transfert.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
	 boolean existsByAccountNumber(String accountNumber);
}

