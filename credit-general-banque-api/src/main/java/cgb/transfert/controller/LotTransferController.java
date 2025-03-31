package cgb.transfert.controller;

import org.springframework.web.bind.annotation.*;

import cgb.transfert.entity.LotTransfer;
import cgb.transfert.service.LotTransferService;

import java.util.List;

@RestController
@RequestMapping("/lots")
public class LotTransferController {
    private final LotTransferService lotTransferService;

    public LotTransferController(LotTransferService lotTransferService) {
        this.lotTransferService = lotTransferService;
    }

    @GetMapping
    public List<LotTransfer> getAllLots() {
        return lotTransferService.getAllLots();
    }
    
   
}

