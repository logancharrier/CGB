package cgb.transfert.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "customer")
    private Set<User> users; 

    @ManyToMany
    @JoinTable(
        name = "customer_account", // Ou "myaccounts" si tu veux
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    private Set<Account> myaccounts;

    @OneToMany
    @JoinTable(
        name = "recipient_account",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    private Set<Account> recipientAccounts;
	
	public Set<Account> getMyaccounts() {
		return myaccounts;
	}

	public void setMyaccounts(Set<Account> myaccounts) {
		this.myaccounts = myaccounts;
	}

	public Set<Account> getRecipientAccounts() {
		return recipientAccounts;
	}

	public void setRecipientAccounts(Set<Account> recipientAccounts) {
		this.recipientAccounts = recipientAccounts;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
