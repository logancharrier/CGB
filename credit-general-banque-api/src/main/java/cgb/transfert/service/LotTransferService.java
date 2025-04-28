package cgb.transfert.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import cgb.transfert.LotTransferRequest;
import cgb.transfert.dto.LotTransferDTO;
import cgb.transfert.dto.TransferDTO;
import cgb.transfert.entity.LotTransfer;
import cgb.transfert.entity.Transfer;
import cgb.transfert.exception.ExceptionLotExiste;
import cgb.transfert.repository.LotTransferRepository;
import cgb.transfert.repository.TransferRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.springframework.http.HttpStatus.CONFLICT;
@Service
public class LotTransferService {
    private final LotTransferRepository lotTransferRepository;
    private final TransferRepository transferRepository;

    public LotTransferService(LotTransferRepository lotTransferRepository, TransferRepository transferRepository) {
        this.lotTransferRepository = lotTransferRepository;
        this.transferRepository = transferRepository;
    }

    @Transactional
    public LotTransfer createLotTransfer(LotTransferDTO lotTransferDTO) throws ExceptionLotExiste {
		Optional<LotTransfer> existingLot = lotTransferRepository.findByRefLot(lotTransferDTO.getRefLot());
		if (existingLot.isPresent()) {
			throw new ExceptionLotExiste();
		}
		
		LotTransfer lot = new LotTransfer(); 
		lot.setRefLot(lotTransferDTO.getRefLot());
		lot.setSourceAccount(lotTransferDTO.getSourceAccount());
		lot.setDescriptionLot(lotTransferDTO.getDescriptionLot());
		lot.setDateLot(LocalDate.now());
		lot.setState("En cours");

		List<Transfer> transfers = new ArrayList<>();

		for (TransferDTO dto : lotTransferDTO.getVirements()) {
		    Transfer transfer = new Transfer();
		    transfer.setDestinationAccount(dto.getDestinationAccount());
		    transfer.setAmount(dto.getAmount());
		    transfer.setDescription(dto.getDescription());
		    transfer.setCompletionDate(LocalDate.now());
		    transfer.setSourceAccount(lotTransferDTO.getSourceAccount());
		    transfer.setLotTransfer(lot);  

		    transfers.add(transfer);
		}
		lot.setTransfers(transfers);
		lotTransferRepository.save(lot);
		
		return lot;
	}

    @Async
    public void executeLotTransfer(LotTransfer lotTransfer) {
    	
    	//Traiter change virement
    	//Collecter le nombre d'erreur
    	//Changer l'état de chaque transfer
    	//Changer l'état du lot
    	//Log
    }
    
    public LotTransfer submitLot(LotTransferDTO lotTransferDTO) throws ExceptionLotExiste {
    	LotTransfer lt = createLotTransfer(lotTransferDTO);
    	executeLotTransfer(lt);
    	return lt;
    }

    public List<LotTransfer> getAllLotTransfers() {
		return lotTransferRepository.findAll();
	}
}
