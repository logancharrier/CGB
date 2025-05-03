package cgb.transfert.exception;

public class ExceptionLotIntrouvable extends RuntimeException {
    public ExceptionLotIntrouvable(String refLot) {
        super("Lot introuvable : " + refLot);
    }
}