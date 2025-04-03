package cgb.transfert.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cgb.transfert.LotTransferRequest;
import cgb.transfert.dto.LotTransferDTO;
import cgb.transfert.dto.TransferDTO;
import cgb.transfert.entity.LotTransfer;
import cgb.transfert.entity.Transfer;
import cgb.transfert.repository.LotTransferRepository;
import cgb.transfert.repository.TransferRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LotTransferService {
    private final LotTransferRepository lotTransferRepository;
    private final TransferRepository transferRepository;

    public LotTransferService(LotTransferRepository lotTransferRepository, TransferRepository transferRepository) {
        this.lotTransferRepository = lotTransferRepository;
        this.transferRepository = transferRepository;
    }

    @Transactional
    public LotTransfer createLotTransfer(LotTransferDTO lotTransferDTO) {
		Optional<LotTransfer> existingLot = lotTransferRepository.findByRefLot(lotTransferDTO.getRefLot());
		if (existingLot.isPresent()) {
			throw new RuntimeException("Ce lot existe déjà !");
		}

		
		LotTransfer newLotTransfer = new LotTransfer(); 
		newLotTransfer.setRefLot(lotTransferDTO.getRefLot());
		newLotTransfer.setSourceAccount(lotTransferDTO.getSourceAccount());
		newLotTransfer.setDescriptionLot(lotTransferDTO.getDescriptionLot());
		newLotTransfer.setDateLot(LocalDate.now());
		newLotTransfer.setState("En cours");

		lotTransferRepository.saveAndFlush(newLotTransfer); 

		List<Transfer> transfers = lotTransferDTO.getVirements().stream().map(dto -> {
			Transfer transfer = new Transfer();
			transfer.setDestinationAccount(dto.getDestinationAccount());
			transfer.setAmount(dto.getAmount());
			transfer.setDescription(dto.getDescription());
			transfer.setCompletionDate(LocalDate.now());
			transfer.setLotTransfer(newLotTransfer); 
			transfer.setSourceAccount(lotTransferDTO.getSourceAccount());

			return transfer;
		}).collect(Collectors.toList());

		transferRepository.saveAll(transfers);
		newLotTransfer.setTransfers(transfers);
		return newLotTransfer;
	}





    public List<LotTransfer> getAllLotTransfers() {
		return lotTransferRepository.findAll();
	}
}
