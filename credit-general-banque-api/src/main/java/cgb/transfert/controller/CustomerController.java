package cgb.transfert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cgb.transfert.entity.Account;
import cgb.transfert.entity.Customer;
import cgb.transfert.exception.ExceptionCustomerNotFound;
import cgb.transfert.repository.UserRepository;
import cgb.transfert.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or @accessControl.isUserOfCustomer(authentication, #id)")
	public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
		return customerService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('COMPTABLE') and @accessControl.isUserOfCustomer(authentication, #id)")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
		if (!customerService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		customer.setId(id);
		return ResponseEntity.ok(customerService.save(customer));
	}

	@PostMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
		return ResponseEntity.ok(customerService.save(customer));
	}

	@GetMapping("/S/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('COMPTABLE') and @accessControlComptable.isUserOfCustomer(authentication, #id)")
	public ResponseEntity<Customer> getCustomerWithS(@PathVariable Long id, @RequestBody Customer customer) {
		if (!customerService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		customer.setId(id);
		return ResponseEntity.ok(customerService.save(customer));
	}

	@GetMapping("/contient-S")
	@PreAuthorize("hasRole('ADMIN') or hasRole('COMPTABLE') and @accessControlComptable.isUserOfCustomer(authentication, #id)")
	public ResponseEntity<String> getCustomerWithtest() {
		return ResponseEntity.ok("HasRole is OK");
	}

	@PostMapping("/{id}/addAccount")
	@PreAuthorize("hasRole('ADMIN') or @accessControl.isUserOfCustomer(authentication, #id)")
	public ResponseEntity<Customer> addAccountToCustomer(@PathVariable Long id, @RequestBody Account account) {

		try {
			Customer updatedCustomer = customerService.addAccountToCustomer(id, account);
			return ResponseEntity.ok(updatedCustomer);
		} catch (ExceptionCustomerNotFound e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PostMapping("/{id}/addRecipient")
	@PreAuthorize("hasRole('ADMIN') or @accessControl.isUserOfCustomer(authentication, #id)")
	public ResponseEntity<Customer> addRecipientAccount(@PathVariable Long id, @RequestBody Account account) {

		try {
			Customer updatedCustomer = customerService.addRecipientAccount(id, account);
			return ResponseEntity.ok(updatedCustomer);
		} catch (ExceptionCustomerNotFound e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
