package cgb.transfert.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cgb.transfert.entity.Account;
import cgb.transfert.repository.AccountRepository;
import cgb.transfert.service.AccountService;
import cgb.transfert.service.LotTransferService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
	
	private final AccountService accountService;
	
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllAccounts() {
		try {
			List<Account> accounts = accountService.getAllAccounts();
			if (accounts.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Aucun compte bancaire trouvé.");
			}
			return ResponseEntity.ok(accounts);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erreur lors de la récupération des comptes.");
		}
	}
}
