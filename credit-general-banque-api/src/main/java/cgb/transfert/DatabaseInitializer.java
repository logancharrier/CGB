package cgb.transfert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cgb.transfert.entity.User;
import cgb.transfert.entity.Customer;
import cgb.transfert.entity.Role;
import cgb.transfert.service.MyUserDetailsService;

import cgb.transfert.entity.Account;
import cgb.transfert.repository.AccountRepository;
import cgb.transfert.utils.IbanGenerator;
import jakarta.annotation.PostConstruct;

import java.util.HashSet;
import java.util.Set;

@Component
public class DatabaseInitializer {

	private final MyUserDetailsService userService;
	
	@Autowired
	AccountRepository accountRepository;

	@Autowired
	public DatabaseInitializer(MyUserDetailsService userService) {
		this.userService = userService;
	}

    private static final int IBAN_COUNT = 20; // Nombre d'IBAN à générer

    @PostConstruct
    public void init() {
    	if (userService.count() == 0) {
			// Appeler une méthode qui crée un jeu d’exemple et qui les sauvegarde
			//  par le biais du repository
			initUser(userService);
		}	
    	if (accountRepository.count() == 0) {
			// Appeler une méthode qui crée un jeu d’exemple et qui les sauvegarde
			//  par le biais du repository
    		insertGeneratedIbans(accountRepository);
		}	
    }
    
    public void initUser(MyUserDetailsService us) {

		User u1=new User();
		u1.setUsername("Hubble");
		u1.setPassword("password");
		u1.setRole(Role.ADMIN);

		User u2=new User();
		u2.setUsername("user");
		u2.setPassword("password");
		u2.setRole(Role.USER);


		User u3=new User();
		u3.setUsername("root");
		u3.setPassword("root");
		u3.setRole(Role.ADMIN);

		User u4=new User();
		u4.setUsername("comptable");
		u4.setPassword("password");
		u4.setRole(Role.COMPTABLE);

		us.registerUser(u1);
		us.registerUser(u2);
		us.registerUser(u3);
		us.registerUser(u4);
	}

    private void insertGeneratedIbans(AccountRepository accountRepository) {
        Set<String> uniqueIbans = new HashSet<>();

        // Générer 20 IBAN uniques
        while (uniqueIbans.size() < IBAN_COUNT) {
            String newIban = IbanGenerator.generateValidIban();
            if (!accountRepository.existsByAccountNumber(newIban)) {
                uniqueIbans.add(newIban);
            }
        }

        // Insérer les IBAN dans la base
        for (String iban : uniqueIbans) {
            Account account = new Account();
            account.setAccountNumber(iban);
            account.setSolde(1000.00); // Solde par défaut
            accountRepository.save(account);
        }

        System.out.println("20 IBAN générés et insérés dans la base.");
    }
}
