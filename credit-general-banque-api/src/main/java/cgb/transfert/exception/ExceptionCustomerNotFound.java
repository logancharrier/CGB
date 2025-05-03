package cgb.transfert.exception;

import cgb.transfert.entity.Customer;

public class ExceptionCustomerNotFound extends IllegalArgumentException {

    public ExceptionCustomerNotFound(Long customerId) {
        super("Customer non trouvé avec l'ID : " + customerId);
    }
}
