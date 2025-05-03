package cgb.transfert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import cgb.transfert.TransferRequest;
import cgb.transfert.entity.Account;
import cgb.transfert.entity.LotTransfer;
import cgb.transfert.entity.Transfer;
import cgb.transfert.exception.ExceptionNonRecipient;
import cgb.transfert.service.LotTransferService;
import cgb.transfert.service.TransferService;
import ch.qos.logback.core.model.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

	@Autowired
	private TransferService transferService;

	@GetMapping("/all")
	
	public ResponseEntity<?> getAllTransfers() {
		try {
			List<Transfer> transfers = transferService.getAllTranfers();
			if (transfers.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Aucun compte bancaire trouvé.");
			}
			return ResponseEntity.ok(transfers);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erreur lors de la récupération des comptes.");
		}
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('COMPTABLE')")
	public ResponseEntity<?> createTransfer(@RequestBody TransferRequest transferRequest) {
	    try {
	        Transfer transfer = transferService.createTransfer(
	            transferRequest.getSourceAccountNumber(),
	            transferRequest.getDestinationAccountNumber(),
	            transferRequest.getAmount(),
	            transferRequest.getTransferDate(),
	            transferRequest.getDescription(),
	            transferRequest.getLotId()
	        );

	        return ResponseEntity.ok(transfer);

	    } catch (ExceptionNonRecipient e) {
	        TransferResponse errorResponse = new TransferResponse("FAILURE", e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

	    } catch (RuntimeException e) {
	        TransferResponse errorResponse = new TransferResponse("FAILURE", e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    }
	}

}

class TransferResponse {
	private String status;
	private String message;

	// Constructeur
	public TransferResponse(String status, String message) {
		this.status = status;
		this.message = message;
	}

	// Getters et Setters
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
