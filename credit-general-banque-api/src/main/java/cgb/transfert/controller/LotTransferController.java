package cgb.transfert.controller;

import cgb.transfert.dto.LotTransferDTO;
import cgb.transfert.dto.TransferDTO;
import cgb.transfert.entity.LotTransfer;
import cgb.transfert.service.LotTransferService;
import org.springframework.http.ResponseEntity;
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
	public LotTransfer createLotTransfer(@RequestBody LotTransferDTO lotTransferDTO) {
		return lotTransferService.createLotTransfer(lotTransferDTO);
	}

    @GetMapping("/all")
    public ResponseEntity<List<LotTransferDTO>> getAllLots() {
		List<LotTransferDTO> lots = lotTransferService.getAllLotTransfers().stream().map(LotTransferDTO::fromEntity)
				.collect(Collectors.toList());
		return ResponseEntity.ok(lots);
	}

}
