package cgb.transfert.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import cgb.transfert.security.AccessControl;

import cgb.transfert.dto.LotTransferDTO;
import cgb.transfert.dto.TransferDTO;
import cgb.transfert.entity.Account;
import cgb.transfert.entity.LotTransfer;
import cgb.transfert.entity.Transfer;
import cgb.transfert.entity.User;
import cgb.transfert.exception.ExceptionLotExiste;
import cgb.transfert.exception.ExceptionLotIntrouvable;
import cgb.transfert.exception.ExceptionNonRecipient;
import cgb.transfert.repository.AccountRepository;
import cgb.transfert.repository.LotTransferRepository;
import cgb.transfert.repository.TransferRepository;
import cgb.transfert.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LotTransferService {
	private final LotTransferRepository lotTransferRepository;
	private final TransferRepository transferRepository;
	private final AccountRepository accountRepo;
	private final LogService log;
	
    @Autowired
    private AccessControl accessControl;
	
    @Autowired
    private UserRepository userRepository;

	public LotTransferService(LotTransferRepository lotTransferRepository, TransferRepository transferRepository,
			AccountRepository accountRepo, LogService log) {
		this.lotTransferRepository = lotTransferRepository;
		this.transferRepository = transferRepository;
		this.accountRepo = accountRepo;
		this.log = log;
	}

	@Transactional
	public LotTransfer createLotTransfer(LotTransferDTO lotTransferDTO) throws ExceptionLotExiste {
		Optional<LotTransfer> existingLot = lotTransferRepository.findByRefLot(lotTransferDTO.getRefLot());
		if (existingLot.isPresent()) {
			log.warn(getClass(), "Lot déjà existant ref=" + lotTransferDTO.getRefLot());
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

		log.info(getClass(), "Lot créé ref=" + lot.getRefLot() + " nbVirements=" + transfers.size());

		return lot;
	}

	@Async
	public void executeLotTransfer(LotTransfer lotTransfer) {
		// Traiter chaque virement
		// Collecter le nombre d'erreur
		// Changer l'état de chaque transfer
		// Changer l'état du lot
		// Log
		LotTransfer lot = lotTransferRepository.findById(lotTransfer.getId()).orElseThrow();
		log.info(getClass(), "Début exécution lot " + lot.getRefLot());
		int erreurs = 0;

		for (Transfer t : new ArrayList<>(lot.getTransfers())) {
			log.info(getClass(),
					"Virement id=" + t.getId() + " dest=" + t.getDestinationAccount() + " montant=" + t.getAmount());
			try {

				Account src = accountRepo.findById(t.getSourceAccount()).orElseThrow();
				Account dest = accountRepo.findById(t.getDestinationAccount()).orElseThrow();

				if (src.getSolde() < t.getAmount()) {
					throw new IllegalStateException("Solde insuffisant");
				}

				// Maj des soldes
				src.setSolde(src.getSolde() - t.getAmount());
				dest.setSolde(dest.getSolde() + t.getAmount());
				accountRepo.save(src);
				accountRepo.save(dest);

				t.setState("Succès");
				log.info(getClass(), "Virement réussi id=" + t.getId());

			} catch (Exception e) {
				erreurs++;
				t.setState("Échec");
				log.warn(getClass(), "Virement échoué id=" + t.getId() + " : " + e.getMessage());
			}

			lot.setState(erreurs == 0 ? "TERMINÉ" : "ÉCHEC");
			lotTransferRepository.save(lot);
			log.info(getClass(), "Fin lot " + lot.getRefLot() + " état=" + lot.getState() + " erreurs=" + erreurs);

		}

	}

	public LotTransfer submitLot(LotTransferDTO lotTransferDTO) throws ExceptionLotExiste, ExceptionNonRecipient {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userRepository.findByUsername(auth.getName()).orElseThrow();

	    // ⚠️ Vérification sécurité fonctionnelle ici
	    boolean autorise = accessControl.isAllowedLotTransfer(user, lotTransferDTO);
	    if (!autorise) {
	        throw new ExceptionNonRecipient();
	    }
		LotTransfer lt = createLotTransfer(lotTransferDTO);
		executeLotTransfer(lt);
		return lt;
	}

	public List<LotTransfer> getAllLotTransfers() {
		return lotTransferRepository.findAll();
	}

	public LotTransfer getLotByRef(String refLot) throws ExceptionLotIntrouvable {
		return lotTransferRepository.findByRefLot(refLot).orElseThrow(() -> new ExceptionLotIntrouvable(refLot));
	}
}
