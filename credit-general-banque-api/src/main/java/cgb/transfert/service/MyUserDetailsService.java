package cgb.transfert.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cgb.transfert.entity.Customer;
import cgb.transfert.entity.Role;
import cgb.transfert.entity.User;
import cgb.transfert.exception.ExceptionCustomerNotFound;
import cgb.transfert.exception.ExceptionUserNotFound;
import cgb.transfert.repository.CustomerRepository;
import cgb.transfert.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	@Autowired
	private CustomerRepository customerRepository;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public MyUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;

	}

	/*
	 * public UserService(UserRepository userRepository) { this.userRepository =
	 * userRepository; }
	 */

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return getUserDetailsFromUser(user);
	}

	// Attention Convertion du User en User détail. Obtention par le service.
	// Ou possibilité de laisser obtention par le Userdetail qui impléments
	// Userdetails
	private UserDetails getUserDetailsFromUser(User user) {
		// Construction des autorités à partir des roles du USER.
		List<GrantedAuthority> authorities = new ArrayList<>();
		Role role = user.getRole();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
		// construction d'un spring user details
		// User spring pas le mien.
		org.springframework.security.core.userdetails.User springUser = new org.springframework.security.core.userdetails.User(
				user.getUsername(), user.getPassword(), authorities);
		return springUser;
	}

	public User registerUser(User user) {
		// Encoder le mot de passe
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		// Sauvegarder l'utilisateur dans la base de données
		return userRepository.save(user);
	}

	public Long count() {
		return userRepository.count();
	}

	public User createUser(User user, Long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ExceptionCustomerNotFound(customerId));

		user.setCustomer(customer);
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		return userRepository.save(user);
	}

	public User updateUser(Long id, User updatedUser, Long customerId) {
		User user = userRepository.findById(id).orElseThrow(() -> new ExceptionUserNotFound(id));

		if (updatedUser.getUsername() != null)
			user.setUsername(updatedUser.getUsername());

		if (updatedUser.getPassword() != null)
			user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

		if (updatedUser.getRole() != null)
			user.setRole(updatedUser.getRole());

		if (customerId != null) {
			Customer customer = customerRepository.findById(customerId)
					.orElseThrow(() -> new ExceptionCustomerNotFound(customerId));
			user.setCustomer(customer);
		}

		return userRepository.save(user);
	}

}
