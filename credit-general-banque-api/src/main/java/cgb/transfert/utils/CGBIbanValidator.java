package cgb.transfert.utils;


import org.apache.commons.validator.routines.IBANValidator;

import cgb.transfert.exception.ExceptionInvalidIbanFormat;
import cgb.transfert.exception.ExceptionInvalidUnCheckableIban;

public class CGBIbanValidator {
    private static CGBIbanValidator instance;
    private static final IBANValidator ibanValidator = IBANValidator.getInstance();

    private CGBIbanValidator() {}

    public static CGBIbanValidator getInstanceValidator() {
        if (instance == null) {
            instance = new CGBIbanValidator();
        }
        return instance;
    }

    public boolean isIbanStructureValide(String iban) throws ExceptionInvalidIbanFormat {
        if (!iban.matches("^FR\\d{2}\\d{5}\\d{5}\\d{11}\\d{2}$")) {
            throw new ExceptionInvalidIbanFormat("Format IBAN invalide : " + iban);
        }
        return true;
    }

    public boolean isIbanValide(String iban) throws ExceptionInvalidUnCheckableIban {
        if (!ibanValidator.isValid(iban)) {
            throw new ExceptionInvalidUnCheckableIban("IBAN non vérifiable ou invalide : " + iban);
        }
        return true;
    }

    public String getCodePays(String iban) throws ExceptionInvalidUnCheckableIban {
        if (isIbanValide(iban)) {
            return iban.substring(0, 2);
        }
        throw new ExceptionInvalidUnCheckableIban("Impossible d'extraire le code pays.");
    }

    public String getChiffreControle(String iban) throws ExceptionInvalidUnCheckableIban {
        if (isIbanValide(iban)) {
            return iban.substring(2, 4);
        }
        throw new ExceptionInvalidUnCheckableIban("Impossible d'extraire les chiffres de contrôle.");
    }

    public String getBBAN(String iban) throws ExceptionInvalidUnCheckableIban {
        if (isIbanValide(iban)) {
            return iban.substring(4);
        }
        throw new ExceptionInvalidUnCheckableIban("Impossible d'extraire le BBAN.");
    }
}
