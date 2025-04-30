package cgb.transfert.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cgb.transfert.entity.Account;
import cgb.transfert.entity.LotTransfer;
import cgb.transfert.entity.Transfer;
import cgb.transfert.repository.AccountRepository;
import cgb.transfert.repository.TransferRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransferService {

	@Autowired
	private TransferRepository transferRepository;

	public Transfer enregistrerLot(Transfer transfer) {
		return transferRepository.save(transfer);
	}

	public List<Transfer> getAllTranfers() {
		return transferRepository.findAll();
	}
}
