package cgb.transfert.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cgb.transfert.entity.Account;
import cgb.transfert.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}
}
