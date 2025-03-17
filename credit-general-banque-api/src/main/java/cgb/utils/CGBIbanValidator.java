package cgb.utils;

import org.apache.commons.validator.routines.IBANValidator;

public class CGBIbanValidator {
	private static CGBIbanValidator instance;
    private static IBANValidator ibanValidator = IBANValidator.getInstance();
	
	private CGBIbanValidator() {}
	
	public static CGBIbanValidator getInstanceValidator() {
		if (instance == null) {
			instance = new CGBIbanValidator();
		}
		return instance;
	}
	
	
	public boolean isIbanStructureValide(String iban) {
		 return iban.matches("^FR\\d{2}\\d{5}\\d{5}\\d{11}\\d{2}$");
	}
	
	public boolean isIbanValide(String iban) {
		return ibanValidator.isValid(iban);
	}
	
	public String getCodePays(String iban) {
        if (isIbanValide(iban)) {
            return iban.substring(0, 2);
        }
        return "IBAN non valide";
    }
	
	public String getChiffreControle(String iban) {
        if (isIbanValide(iban)) {
            return iban.substring(2, 4);
        }
        return "IBAN non valide";
    }
	
	public String getBBAN(String iban) {
        if (isIbanValide(iban)) {
            return iban.substring(4);
        }
        return "IBAN non valide";
    }
}
