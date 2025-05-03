package cgb.transfert.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cgb.transfert.entity.Log;
import cgb.transfert.repository.LogRepository;

@RestController
@RequestMapping("/api/logs")
public class LogController {
	LogRepository logRepository;

	LogController(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	@GetMapping("/all")
	public List<Log> getAll() {
		return logRepository.findAll();
	}
}
