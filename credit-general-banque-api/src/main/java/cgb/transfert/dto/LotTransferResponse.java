package cgb.transfert.dto;

public class LotTransferResponse {
	private String numLot;
	private String dateLancement;
	private String message;
	private String etat;

	public LotTransferResponse(String numLot, String dateLancement, String message, String etat) {
		this.numLot = numLot;
		this.dateLancement = dateLancement;
		this.message = message;
		this.etat = etat;
	}

	// Getters et Setters
	public String getNumLot() {
		return numLot;
	}

	public void setNumLot(String numLot) {
		this.numLot = numLot;
	}

	public String getDateLancement() {
		return dateLancement;
	}

	public void setDateLancement(String dateLancement) {
		this.dateLancement = dateLancement;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}
}