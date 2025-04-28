package cgb.transfert.dto;

import lombok.Data;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import cgb.transfert.entity.Transfer;

@Data
public class TransferDTO {
	private Long id;
	@JsonProperty("destAccount")
	@JsonAlias("destinationAccount")
	private String destinationAccount;
	private Double amount;
	private String description;
	private LocalDate completionDate;
	private String state;

	public static TransferDTO fromEntity(Transfer transfer) {
		TransferDTO dto = new TransferDTO();
		dto.id = transfer.getId();
		dto.destinationAccount = transfer.getDestinationAccount();
		dto.amount = transfer.getAmount();
		dto.description = transfer.getDescription();
		dto.completionDate = transfer.getCompletionDate();
		dto.state = transfer.getState();
		return dto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDestinationAccount() {
		return destinationAccount;
	}

	public void setDestinationAccount(String destinationAccount) {
		this.destinationAccount = destinationAccount;
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

	public LocalDate getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(LocalDate completionDate) {
		this.completionDate = completionDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
