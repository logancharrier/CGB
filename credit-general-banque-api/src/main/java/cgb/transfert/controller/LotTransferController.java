package cgb.transfert.controller;

import cgb.transfert.dto.LotTransferDTO;
import cgb.transfert.dto.LotTransferResponse;
import cgb.transfert.dto.TransferDTO;
import cgb.transfert.entity.LotTransfer;
import cgb.transfert.exception.ExceptionLotExiste;
import cgb.transfert.exception.ExceptionLotIntrouvable;
import cgb.transfert.exception.ExceptionNonRecipient;
import cgb.transfert.service.LotTransferService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
	@PreAuthorize("hasRole('ADMIN') or hasRole('COMPTABLE')")
	public ResponseEntity<?> createLotTransfer(@RequestBody LotTransferDTO lotTransferDTO)
			throws ExceptionLotExiste, ExceptionNonRecipient {
		try {
			LotTransfer lotEnCours = lotTransferService.submitLot(lotTransferDTO);

			LotTransferResponse body = new LotTransferResponse(lotEnCours.getId().toString(), // numLot
					lotEnCours.getDateLot().toString(), // dateLancement (yyyy-MM-dd)
					"Traitement Lancé", // message
					"EnCours" // etat
			);
			return ResponseEntity.status(HttpStatus.CREATED).body(body);

		} catch (ExceptionNonRecipient nr) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(nr.getMessage());

		} catch (ExceptionLotExiste le) {
			return ResponseEntity.status(HttpStatus.CONFLICT) // 409
					.body(le.getMessage());
		}
	}

	@GetMapping("/all")
	public ResponseEntity<List<LotTransferDTO>> getAllLots() {
		List<LotTransfer> entities = lotTransferService.getAllLotTransfers();

		List<LotTransferDTO> dtos = new ArrayList<>();
		for (LotTransfer lot : entities) {
			dtos.add(LotTransferDTO.fromEntity(lot));
		}

		return ResponseEntity.ok(dtos);
	}

	@GetMapping("/{numLot}")
	public ResponseEntity<?> getLotByNum(@PathVariable String numLot) {
		try {
			LotTransfer lot = lotTransferService.getLotByRef(numLot);
			return ResponseEntity.ok(LotTransferDTO.fromEntity(lot));

		} catch (ExceptionLotIntrouvable e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND) // 404
					.body(e.getMessage());
		}
	}

}
