package cgb.transfert.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cgb.transfert.entity.Account;
import cgb.transfert.entity.Customer;
import cgb.transfert.exception.ExceptionCustomerNotFound;
import cgb.transfert.repository.AccountRepository;
import cgb.transfert.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private AccountRepository accountRepository;

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Iterable<Customer> findAll() {
        return customerRepository.findAll();
    }
    
    public Customer addAccountToCustomer(Long customerId, Account account) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new ExceptionCustomerNotFound(customerId));

        // On suppose que le compte existe déjà, sinon on le crée
        if (!accountRepository.existsById(account.getAccountNumber())) {
            accountRepository.save(account);
        }

        customer.getMyaccounts().add(account);
        return customerRepository.save(customer);
    }
    
    public Customer addRecipientAccount(Long customerId, Account account) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new ExceptionCustomerNotFound(customerId));

        // Sauvegarde le compte s’il n’existe pas encore
        if (!accountRepository.existsById(account.getAccountNumber())) {
            accountRepository.save(account);
        }

        customer.getRecipientAccounts().add(account);
        return customerRepository.save(customer);
    }
}
