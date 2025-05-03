package cgb.transfert;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransferRequest {
	@JsonProperty("sourceAccount")
	private String sourceAccountNumber;

	@JsonProperty("destinationAccount")
	private String destinationAccountNumber;

	@JsonProperty("amount")
	private Double amount;

	@JsonProperty("completionDate")
	private LocalDate transferDate;

	@JsonProperty("description")
	private String description;
	
	@JsonProperty("lotId")
    private Long lotId;

	public Long getLotId() {
		return lotId;
	}

	public void setLotId(Long lotId) {
		this.lotId = lotId;
	}

	/* getters / setters */
	public String getSourceAccountNumber() {
		return sourceAccountNumber;
	}

	public void setSourceAccountNumber(String sourceAccountNumber) {
		this.sourceAccountNumber = sourceAccountNumber;
	}

	public String getDestinationAccountNumber() {
		return destinationAccountNumber;
	}

	public void setDestinationAccountNumber(String destinationAccountNumber) {
		this.destinationAccountNumber = destinationAccountNumber;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDate getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(LocalDate transferDate) {
		this.transferDate = transferDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
