package cgb.transfert.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
public class Account {
    @Id
    private String accountNumber;
	private Double solde;

	public Double getSolde() {
		return solde;
	}
	
	public void setSolde(Double solde) {
		this.solde = solde;
	}
	
    public String getAccountNumber() {
		return accountNumber;
	}
    
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
}