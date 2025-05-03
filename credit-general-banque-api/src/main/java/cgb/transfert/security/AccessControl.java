package cgb.transfert.security;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import cgb.transfert.dto.LotTransferDTO;
import cgb.transfert.entity.User;
import cgb.transfert.repository.UserRepository;

@Component
public class AccessControl {

    private final UserRepository userRepository;

    public AccessControl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isUserOfCustomer(Authentication authentication, Long customerId) {
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .map(user -> user.getCustomer().getId().equals(customerId))
                .orElse(false);
    }
    
    public boolean isAllowedTransfer(User user, String sourceIban, String destIban) {
        var accounts = user.getCustomer().getMyaccounts();
        var recipients = user.getCustomer().getRecipientAccounts();

        boolean sourceOk = accounts.stream()
            .anyMatch(a -> a.getAccountNumber().equals(sourceIban));

        boolean destOk = recipients.stream()
            .anyMatch(a -> a.getAccountNumber().equals(destIban))
            || accounts.stream()
            .anyMatch(a -> a.getAccountNumber().equals(destIban));

        return sourceOk && destOk;
    }
    
    public boolean isAllowedLotTransfer(User user, LotTransferDTO lotDto) {
        var customer = user.getCustomer();
        var myAccounts = customer.getMyaccounts();
        var recipients = customer.getRecipientAccounts();

        // Vérifie que le compte source du lot appartient bien au client
        boolean sourceOk = myAccounts.stream()
            .anyMatch(acc -> acc.getAccountNumber().equals(lotDto.getSourceAccount()));

        // Vérifie que tous les comptes destinataires sont autorisés
        boolean allDestOk = lotDto.getVirements().stream().allMatch(t ->
            recipients.stream().anyMatch(acc -> acc.getAccountNumber().equals(t.getDestinationAccount())) ||
            myAccounts.stream().anyMatch(acc -> acc.getAccountNumber().equals(t.getDestinationAccount()))
        );

        return sourceOk && allDestOk;
    }

}
