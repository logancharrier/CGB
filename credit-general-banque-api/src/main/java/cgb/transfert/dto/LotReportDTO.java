package cgb.transfert.dto;

import java.util.List;

public class LotReportDTO  {
	
	private String refLot;
	private String state;
	private List<TransferReportDTO> transfers;

	public LotReportDTO(String refLot, String state, List<TransferReportDTO> transfers) {
		super();
		this.refLot = refLot;
		this.state = state;
		this.transfers = transfers;
	}
	
	public String getRefLot() {
		return refLot;
	}

	public void setRefLot(String refLot) {
		this.refLot = refLot;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<TransferReportDTO> getTransfers() {
		return transfers;
	}

	public void setTransfers(List<TransferReportDTO> transfers) {
		this.transfers = transfers;
	}

}
