package cgb.transfert.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "TRANSFER")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DEST_ACCOUNT", nullable = false)
    private String destinationAccount;

    @Column(name = "SOURCE_ACCOUNT", nullable = false)
    private String sourceAccount;
    
    

	@Column(name = "AMOUNT", nullable = false)
    private Double amount;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "COMPLETION_DATE")
    private LocalDate completionDate;

    @Column(name = "STATE")
    private String state;

    @ManyToOne
    @JoinColumn(name = "LOT_ID")
    @JsonBackReference
    private LotTransfer lotTransfer;

    public Transfer() {
        this.state = "pending"; // État initial des transferts
    }

    // Getters et Setters

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

    public String getSourceAccount() {
		return sourceAccount;
	}

	public void setSourceAccount(String sourceAccount) {
		this.sourceAccount = sourceAccount;
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

    public LotTransfer getLotTransfer() {
        return lotTransfer;
    }

    public void setLotTransfer(LotTransfer lotTransfer) {
        this.lotTransfer = lotTransfer;
    }
}
