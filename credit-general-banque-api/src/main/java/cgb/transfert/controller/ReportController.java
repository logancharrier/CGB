package cgb.transfert.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cgb.transfert.dto.TransferReportDTO;
import cgb.transfert.exception.ExceptionLotIntrouvable;
import cgb.transfert.service.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // rapport complet d’un lot 
    @GetMapping("/lots/{numLot}")
    public ResponseEntity<?> fullReportLot(@PathVariable String numLot) {
        try {
            return ResponseEntity.ok(reportService.lotReport(numLot));
        } catch (ExceptionLotIntrouvable e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // échecs d’un lot 
    @GetMapping("/failed/lot/{numLot}")
    public ResponseEntity<?> failedLot(@PathVariable String numLot) {
        try {
            return ResponseEntity.ok(reportService.failedByLot(numLot));
        } catch (ExceptionLotIntrouvable e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // échecs sur intervalle de dates
    @GetMapping("/failed/dates")
    public List<TransferReportDTO> failedDates(
            @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate to) {
        return reportService.failedByDates(from, to);
    }

    // échecs pour un destinataire
    @GetMapping("/failed/dest/{iban}")
    public List<TransferReportDTO> failedDest(@PathVariable String iban) {
        return reportService.failedByDest(iban);
    }
}

