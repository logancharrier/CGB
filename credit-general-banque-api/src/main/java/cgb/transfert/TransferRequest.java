package cgb.transfert;


import java.time.LocalDate;
import lombok.*;

@Data
public class TransferRequest {
	private String destAccount;
    
	private Double amount;
    private String description;
	
    public String getDestAccount() {
		return destAccount;
	}
	public void setDestAccount(String destAccount) {
		this.destAccount = destAccount;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
