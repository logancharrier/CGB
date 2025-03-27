package cgb.transfert.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class LotTransfer {
    @Id
    private String numeroDeLot;
	private LocalDateTime dateLancement;
    private String etat;
    
    @OneToMany(mappedBy = "lotTransfert", cascade = CascadeType.ALL)
    private List<Transfer> transfers;

    public LotTransfer() {
        this.numeroDeLot = UUID.randomUUID().toString();
        this.dateLancement = LocalDateTime.now();
        this.etat = "En cours";
    }

    public String getNumeroDeLot() {
		return numeroDeLot;
	}

	public void setNumeroDeLot(String numeroDeLot) {
		this.numeroDeLot = numeroDeLot;
	}

	public LocalDateTime getDateLancement() {
		return dateLancement;
	}

	public void setDateLancement(LocalDateTime dateLancement) {
		this.dateLancement = dateLancement;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public List<Transfer> getTransfers() {
		return transfers;
	}

	public void setTransfers(List<Transfer> transfers) {
		this.transfers = transfers;
	}
}