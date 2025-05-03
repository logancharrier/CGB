package cgb.transfert.exception;

public class ExceptionNonRecipient extends Exception {
	public ExceptionNonRecipient() {
        super("Virement refusé : compte destinataire non bénéficiaire ou compte émetteur ne fait pas parti de l'entreprise");
    }
}
