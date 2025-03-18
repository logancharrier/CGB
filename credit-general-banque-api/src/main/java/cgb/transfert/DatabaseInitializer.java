package cgb.transfert;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cgb.transfert.entity.Account;
import cgb.transfert.repository.AccountRepository;
import cgb.transfert.utils.IbanGenerator;
import jakarta.annotation.PostConstruct;

@Component
public class DatabaseInitializer {

    @Autowired
    private AccountRepository accountRepository;

    @PostConstruct
    public void init() {
        // Vérifiez si la base de données est vide avant d'insérer des données
        if (accountRepository.count() == 0) {
           insertSampleData(accountRepository);
        }
    }

    public static void insertSampleData(AccountRepository accountRepository) {
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            Account account = new Account();
            account.setAccountNumber(IbanGenerator.generateValidIban()); 
            account.setSolde(Math.round((random.nextDouble() * 10000) * 100.0) / 100.0); 
            accountRepository.save(account);
        }

        System.out.println("20 comptes avec IBAN valides ont été insérés dans la base.");
    }
}
