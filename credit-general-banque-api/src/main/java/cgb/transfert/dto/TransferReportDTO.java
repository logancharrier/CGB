package cgb.transfert.dto;

public class TransferReportDTO  {
	private Long id; 
	private String dest; 
	private Double amount; 
	private String state;
	
	public TransferReportDTO(Long id, String dest, Double amount, String state) {
		super();
		this.id = id;
		this.dest = dest;
		this.amount = amount;
		this.state = state;
	}
	
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
