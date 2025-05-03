package cgb.transfert.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import cgb.transfert.entity.LotTransfer;

@Data
public class LotTransferDTO {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("ref_lot")         
	@JsonAlias("refLot")            
	private String refLot;
	@JsonProperty("sourceAccount")
	private String sourceAccount;
	@JsonProperty("descriptionLot")
	private String descriptionLot;
	@JsonProperty("dateLot")
	private LocalDate dateLot;
	@JsonProperty("state")
	private String state;
	@JsonProperty("virements")
    private List<TransferDTO> virements; 
    
    
    public static LotTransferDTO fromEntity(LotTransfer lot) {
		LotTransferDTO dto = new LotTransferDTO();
		dto.id = lot.getId();
		dto.refLot = lot.getRefLot();
		dto.sourceAccount = lot.getSourceAccount();
		dto.descriptionLot = lot.getDescriptionLot();
		dto.dateLot = lot.getDateLot();
		dto.state = lot.getState();
		dto.virements = lot.getTransfers().stream().map(TransferDTO::fromEntity).collect(Collectors.toList());
		return dto;
	}
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRefLot() {
		return refLot;
	}
	public void setRefLot(String refLot) {
		this.refLot = refLot;
	}
	public String getSourceAccount() {
		return sourceAccount;
	}
	public void setSourceAccount(String sourceAccount) {
		this.sourceAccount = sourceAccount;
	}
	public String getDescriptionLot() {
		return descriptionLot;
	}
	public void setDescriptionLot(String descriptionLot) {
		this.descriptionLot = descriptionLot;
	}
	public LocalDate getDateLot() {
		return dateLot;
	}
	public void setDateLot(LocalDate dateLot) {
		this.dateLot = dateLot;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<TransferDTO> getVirements() {
		return virements;
	}
	public void setVirements(List<TransferDTO> virements) {
		this.virements = virements;
	}
}
