package cgb.transfert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cgb.transfert.TransferRequest;
import cgb.transfert.entity.Account;
import cgb.transfert.entity.LotTransfer;
import cgb.transfert.entity.Transfer;
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
    
    @Autowired
    private LotTransferService lotTransferService;
    
    @GetMapping("/accounts")
    public ResponseEntity<?> getAllAccounts() {
        try {
            List<Account> accounts = transferService.getAllAccounts();
            if (accounts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Aucun compte bancaire trouvé.");
            }
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération des comptes.");
        }
    }

    /*
    @PostMapping
    public ResponseEntity<String> testTransfer(@RequestBody String s) {
    	System.out.println("Post reçu");
        return ResponseEntity.ok("Post bien traité: "+ s);
    } 
    */
    
//    @PostMapping("/selectionnerLot")
//    public String selectionnerLot(@ModelAttribute Transfer transfer, Model model) {
//        List<LotTransfer> lots = lotTransferService.getAllLots();
//        TransferAndLotDTO dto = new TransferAndLotDTO();
//        dto.setTransfer(transfer);
//        dto.setLots(lots);
//
//        model.addAttribute("transferAndLotDTO", dto);
//        return "formAddTransferToLot";
//    }
//    
//    @PostMapping("/ajouterTransfert")
//    public String ajouterTransfert(@ModelAttribute TransferAndLotDTO dto) {
//        Long lotId = dto.getTransferId();  // Récupération de l'ID du lot sélectionné
//        LotTransfer lotTransfer = lotTransferService.getLotByNumeroDeLot(lotId);
//        Transfer transfer = dto.getTransfer();
//        transfer.setLotTransfer(lotTransfer);  // Association du transfert au lot
//        transferService.enregistrerTransfer(transfer);  // Enregistrement en base
//
//        return "confirmationAjout";
//    }
//    
//    public ResponseEntity<Transfer> sendTransferDTO (@RequestBody TransferAndLotDTO pdto) {
//		return null;
//	}
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
