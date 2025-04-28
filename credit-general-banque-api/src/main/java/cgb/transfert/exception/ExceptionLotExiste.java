package cgb.transfert.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ExceptionLotExiste extends ExceptionLot {

	public ExceptionLotExiste() { super("Erreur : Ce lot existe déjà."); }

}
