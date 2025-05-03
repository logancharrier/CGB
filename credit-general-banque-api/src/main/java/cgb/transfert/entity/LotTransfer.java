package cgb.transfert.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "LOT_TRANSFER")
public class LotTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "NUM_LOT")
    private Long id;
    
    @Column(name = "REF_LOT", unique = true, nullable = false)
    private String refLot;

    @Column(name = "SOURCE_ACCOUNT", nullable = false)
    private String sourceAccount;

    @Column(name = "DESCRIPTION_LOT")
    private String descriptionLot;

    @Column(name = "DATE_LOT")
    private LocalDate dateLot;

    @Column(name = "STATE")
    private String etat;

    @OneToMany(mappedBy = "lotTransfer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Transfer> transfers;

    public LotTransfer() {
        this.dateLot = LocalDate.now();
        this.etat = "receive"; 
    }

    // Getters et Setters

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
        return etat;
    }

    public void setState(String state) {
        this.etat = state;
    }

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<Transfer> transfers) {
        this.transfers = transfers;
    }
}
