package cgb.transfert.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import cgb.transfert.entity.Account;
import cgb.transfert.entity.LotTransfer;
import cgb.transfert.entity.Transfer;
import cgb.transfert.entity.User;
import cgb.transfert.exception.ExceptionNonRecipient;
import cgb.transfert.repository.AccountRepository;
import cgb.transfert.repository.LotTransferRepository;
import cgb.transfert.repository.TransferRepository;
import cgb.transfert.repository.UserRepository;
import cgb.transfert.security.AccessControl;
import jakarta.transaction.Transactional;

@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LotTransferRepository lotTransferRepository;

    @Autowired
    private AccessControl accessControl;

    public List<Transfer> getAllTranfers() {
        return transferRepository.findAll();
    }

    @Transactional
    public Transfer createTransfer(String sourceAccountNumber, String destinationAccountNumber, Double amount,
                                   LocalDate transferDate, String description, Long lotId) throws ExceptionNonRecipient {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();
        
        boolean autorise = accessControl.isAllowedTransfer(user, sourceAccountNumber, destinationAccountNumber);
        if (!autorise) {
            throw new ExceptionNonRecipient();
        }

        // Chargement des comptes
        Account sourceAccount = accountRepository.findById(sourceAccountNumber)
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        Account destinationAccount = accountRepository.findById(destinationAccountNumber)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        // Vérification du solde
        if (sourceAccount.getSolde().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        // Maj des soldes
        sourceAccount.setSolde(sourceAccount.getSolde() - amount);
        destinationAccount.setSolde(destinationAccount.getSolde() + amount);
        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);

        // Ajout dans le lot
        LotTransfer lot = lotTransferRepository.findById(lotId)
                .orElseThrow(() -> new RuntimeException("Lot not found"));

        Transfer transfer = new Transfer();
        transfer.setSourceAccount(sourceAccountNumber);
        transfer.setDestinationAccount(destinationAccountNumber);
        transfer.setAmount(amount);
        transfer.setCompletionDate(transferDate);
        transfer.setDescription(description);
        transfer.setLotTransfer(lot);

        return transferRepository.save(transfer);
    }
}
