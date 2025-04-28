package cgb.transfert.controller;

import cgb.transfert.dto.LotTransferDTO;
import cgb.transfert.dto.LotTransferResponse;
import cgb.transfert.dto.TransferDTO;
import cgb.transfert.entity.LotTransfer;
import cgb.transfert.exception.ExceptionLotExiste;
import cgb.transfert.service.LotTransferService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lot-transfers")
public class LotTransferController {
	private final LotTransferService lotTransferService;

	public LotTransferController(LotTransferService lotTransferService) {
		this.lotTransferService = lotTransferService;
	}

	@PostMapping
	public ResponseEntity<?> createLotTransfer(@RequestBody LotTransferDTO lotTransferDTO) throws ExceptionLotExiste {
		try {
			LotTransfer lotEnCours = lotTransferService.submitLot(lotTransferDTO);

			LotTransferResponse body = new LotTransferResponse(lotEnCours.getId().toString(), // numLot
					lotEnCours.getDateLot().toString(), // dateLancement (yyyy-MM-dd)
					"Traitement Lancé", // message
					"EnCours" // etat
			);
			return ResponseEntity.status(HttpStatus.CREATED).body(body);

		} catch (ExceptionLotExiste le) {
			return ResponseEntity.status(HttpStatus.CONFLICT) // 409
					.body(le.getMessage());
		}
	}

	@GetMapping("/all")
	public ResponseEntity<List<LotTransferDTO>> getAllLots() {
		List<LotTransferDTO> lots = lotTransferService.getAllLotTransfers().stream().map(LotTransferDTO::fromEntity)
				.collect(Collectors.toList());
		return ResponseEntity.ok(lots);
	}

}
