package cgb.transfert.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "LOT_TRANSFER")
public class LotTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "NUM_LOT")
    private Long numeroDeLot;
    
    @Column(name = "DATE_LANCEMENT")
    private LocalDate dateLancement;
    
    @Column(name = "MESSAGE")
    private String message;
    
    @Column(name = "ETAT")
    private String etat;

    @OneToMany(mappedBy = "lotTransfer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Transfer> transfers;

    public LotTransfer() {
        this.dateLancement = LocalDate.now();
        this.etat = "EnCours";
    }

    public Long getNumeroDeLot() {
        return numeroDeLot;
    }

    public void setNumeroDeLot(Long numeroDeLot) {
        this.numeroDeLot = numeroDeLot;
    }

    public LocalDate getDateLancement() {
        return dateLancement;
    }

    public void setDateLancement(LocalDate dateLancement) {
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
