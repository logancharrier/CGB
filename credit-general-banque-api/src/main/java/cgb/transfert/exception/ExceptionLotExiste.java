package cgb.transfert.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ExceptionLotExiste extends ExceptionLot {
	private static final long serialVersionUID = 1L;

	public ExceptionLotExiste() { super("Erreur : Ce lot existe déjà."); }

}
