package cgb.transfert.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cgb.transfert.entity.LotTransfer;
import cgb.transfert.entity.Transfer;
import cgb.transfert.repository.LotTransferRepository;
import cgb.transfert.repository.TransferRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class LotTransferService {
    private final LotTransferRepository lotTransferRepository;
    private final TransferRepository transferRepository;

    public LotTransferService(LotTransferRepository lotTransferRepository, TransferRepository transferRepository) {
        this.lotTransferRepository = lotTransferRepository;
        this.transferRepository = transferRepository;
    }

   
    public List<LotTransfer> getAllLots() {
        return lotTransferRepository.findAll();
    }
}
