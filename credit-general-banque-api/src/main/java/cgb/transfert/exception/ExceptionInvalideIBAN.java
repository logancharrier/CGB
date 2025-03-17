package cgb.transfert.exception;

public abstract class ExceptionInvalideIBAN extends Exception {

	private static final long serialVersionUID = 1L;

	public ExceptionInvalideIBAN() {
        super();
    }

    public ExceptionInvalideIBAN(String message) {
        super(message);
    }

    public ExceptionInvalideIBAN(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionInvalideIBAN(Throwable cause) {
        super(cause);
    }
}
