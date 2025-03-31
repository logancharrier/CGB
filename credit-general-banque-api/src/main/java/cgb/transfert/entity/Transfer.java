package cgb.transfert.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@Entity
@Data
@Table(name = "TRANSFER")
public class Transfer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String sourceAccountNumber; 
	private String destinationAccountNumber; 
	private Double amount;
	private LocalDate transferDate = LocalDate.now(); 
	private String description;

	@Enumerated(EnumType.STRING)
	private TransferStatus etat;

	@ManyToOne
	@JoinColumn(name = "lot_id")
	@JsonManagedReference
	private LotTransfer lotTransfer;

	public enum TransferStatus {
		SUCCESS, ECHEC, CANCELED
	}
}