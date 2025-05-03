package cgb.transfert.exception;

import cgb.transfert.entity.Customer;

public class ExceptionUserNotFound extends IllegalArgumentException {

    public ExceptionUserNotFound(Long userId) {
        super("User non trouvé avec l'ID : " + userId);
    }
}
