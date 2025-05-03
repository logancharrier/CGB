package cgb.transfert.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import cgb.transfert.entity.User;
import cgb.transfert.repository.UserRepository;

@Component
public class AccessControlComptable {

	private final UserRepository userRepository;

	public AccessControlComptable(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	/*
    public boolean isUserOfCustomer(Authentication authentication, Long customerId) {
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .map(user -> user.getCustomer().getId().equals(customerId))
                .orElse(false);
    }
	 */
	
	public boolean isUserOfCustomer(Authentication authentication, Long customerId) {
		String username = authentication.getName();
		User user = (userRepository.findByUsername(username)).orElse(null);
		if (user != null) {
			return user.getCustomer().getName().contains("S");
		} else {
			return false;
		}
	}
}
