package cgb.transfert;

import java.util.List;

public class LotTransferRequest {
    private String refLot;
    private String sourceAccount;
    private String descriptionLot;
    private List<TransferRequest> virements;

    // Getters et Setters
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

    public List<TransferRequest> getVirements() {
        return virements;
    }

    public void setVirements(List<TransferRequest> virements) {
        this.virements = virements;
    }
}
