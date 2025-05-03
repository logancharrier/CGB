package cgb.transfert.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cgb.transfert.dto.UserDTO;
import cgb.transfert.entity.User;
import cgb.transfert.repository.CustomerRepository;
import cgb.transfert.repository.UserRepository;
import cgb.transfert.service.JwtService;
import cgb.transfert.service.MyUserDetailsService;



@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyUserDetailsService userService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/{username}")
    //#id == authentication.principal en 
    //@PreAuthorize("#username == principal.username or hasRole('ADMIN')")
    @PreAuthorize("#username == principal.username or hasAuthority('ROLE_ADMIN')")
    //ATTENTION Avec hasAuthority, il faut rajouter ROLE_
    //@PreAuthorize("permitAll()")
    public ResponseEntity<User> getUser(@PathVariable String username, Principal principal) {
        System.out.println("Principal name: " + principal.getName() +"To string "+ principal.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("<-- Authorities -->: " + authentication.getAuthorities().toString());   
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/auth/{id}")
    //@PreAuthorize("permitAll()")
    public ResponseEntity<User> getUserBy(@PathVariable Long id) {
        //System.out.println("Principal name: " + principal.getName());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("<-- Authorities -->: " + authentication.getAuthorities().toString());
        System.out.println("                   |-- Authority -->: " + authentication.getAuthorities().stream().findFirst());
        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody UserDTO request) {
        User user = new User();
        user.setUsername(request.username);
        user.setPassword(request.password);
        user.setRole(request.role);

        return ResponseEntity.ok(userService.createUser(user, request.customerId));
    }

    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO request) {
        User user = new User();
        user.setUsername(request.username);
        user.setPassword(request.password);
        user.setRole(request.role);

        return ResponseEntity.ok(userService.updateUser(id, user, request.customerId));
    }

}
