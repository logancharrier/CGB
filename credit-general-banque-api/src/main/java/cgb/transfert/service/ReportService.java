package cgb.transfert.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cgb.transfert.dto.LotReportDTO;
import cgb.transfert.dto.TransferReportDTO;
import cgb.transfert.entity.LotTransfer;
import cgb.transfert.entity.Transfer;
import cgb.transfert.exception.ExceptionLotIntrouvable;
import cgb.transfert.repository.LotTransferRepository;
import cgb.transfert.repository.TransferRepository;

@Service
public class ReportService {

	private final LotTransferRepository lotTransferRepository;
	private final TransferRepository transferRepository;

	public ReportService(LotTransferRepository lotTransferRepository, TransferRepository transferRepository) {
		this.lotTransferRepository = lotTransferRepository;
		this.transferRepository = transferRepository;
	}

	// rapport complet d’un lot
	public LotReportDTO lotReport(String refLot) throws ExceptionLotIntrouvable {
		// On récupère le lot ou on lève notre exception
		LotTransfer lot = lotTransferRepository.findByRefLot(refLot)
				.orElseThrow(() -> new ExceptionLotIntrouvable(refLot));

		// Pour chaque virement du lot on crée un DTO
		List<TransferReportDTO> list = new ArrayList<>();
		for (Transfer t : lot.getTransfers()) {
			list.add(new TransferReportDTO(t.getId(), t.getDestinationAccount(), t.getAmount(), t.getState()));
		}

		// On renvoie le résumé du lot + la liste de ses virements
		return new LotReportDTO(lot.getRefLot(), lot.getState(), list);
	}

	// échecs d’un lot
	public List<TransferReportDTO> failedByLot(String refLot) throws ExceptionLotIntrouvable {
		// On récupère le lot ou on lève notre exception
		LotTransfer lot = lotTransferRepository.findByRefLot(refLot)
				.orElseThrow(() -> new ExceptionLotIntrouvable(refLot));

		// Pour chaque virement échoué on crée un DTO
		List<TransferReportDTO> listeTransfersFailed = new ArrayList<>();
		for (Transfer t : lot.getTransfers()) {
			if ("Échec".equals(t.getState())) { // on vérifie si l'état correspond bien à "Échec"
				listeTransfersFailed
						.add(new TransferReportDTO(t.getId(), t.getDestinationAccount(), t.getAmount(), t.getState()));
			}
		}
		return listeTransfersFailed;
	}

	// échecs par intervalle de dates
	public List<TransferReportDTO> failedByDates(LocalDate from, LocalDate to) {
		if (from == null)
			from = LocalDate.MIN; // On met la date from à la borne minimale pour pouvoir comparer ensuite à notre date de completion du virement
		if (to == null)
			to = LocalDate.MAX; // On met la date from à la borne maximale pour pouvoir comparer ensuite à notre date de completion du virement

		List<TransferReportDTO> listeTransfersFailed = new ArrayList<>();
		for (Transfer t : transferRepository.findAll()) {
			boolean transferEnEchec = "Échec".equals(t.getState()); // on vérifie si l'état correspond bien à "Échec"
			boolean comprisEntreLesDates = !t.getCompletionDate().isBefore(from) && !t.getCompletionDate().isAfter(to); // on vérifie si la date de completion est bien comprises entre nos deux dates
			if (transferEnEchec && comprisEntreLesDates) { // Si les deux conditions sont remplies on ajoute à notre liste
				listeTransfersFailed
						.add(new TransferReportDTO(t.getId(), t.getDestinationAccount(), t.getAmount(), t.getState()));
			}
		}
		return listeTransfersFailed;
	}

	// échecs pour un destinataire donné
	public List<TransferReportDTO> failedByDest(String iban) {
		List<TransferReportDTO> listeTransfersFailed = new ArrayList<>();
		for (Transfer t : transferRepository.findAll()) {
			if ("Échec".equals(t.getState()) && iban.equals(t.getDestinationAccount())) {
				listeTransfersFailed
						.add(new TransferReportDTO(t.getId(), t.getDestinationAccount(), t.getAmount(), t.getState()));
			}
		}
		return listeTransfersFailed;
	}
}
