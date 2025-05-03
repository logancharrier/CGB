package cgb.transfert.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cgb.transfert.entity.Log;
import cgb.transfert.repository.LogRepository;

@Service
public class LogService {

	@Autowired
	private LogRepository logRepository;
	
	private void save(String etat, Class<?> classe, String message) {
		Log log = new Log(LocalDate.now(), etat, message, classe.getSimpleName());
		logRepository.save(log);
	}

	public void info(Class<?> classe, String message) {
		save("INFO", classe, message);
	}

	public void warn(Class<?> classe, String message) {
		save("WARN", classe, message);
	}

	public void error(Class<?> classe, String message) {
		save("ERROR", classe, message);
	}

	
}
