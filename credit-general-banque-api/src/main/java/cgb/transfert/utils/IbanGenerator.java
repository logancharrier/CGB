package cgb.transfert.utils;

import java.math.BigInteger;
import java.util.Random;

public class IbanGenerator {
	private static final String COUNTRY_CODE = "FR";
	private static final int IBAN_LENGTH = 27;
	private static final Random RANDOM = new Random();

	public static String generateValidIban() {
		StringBuilder bban = new StringBuilder();
		for (int i = 0; i < 23; i++) {
			bban.append(RANDOM.nextInt(10));
		}
		String checkDigits = calculateCheckDigits(COUNTRY_CODE, bban.toString());
		return COUNTRY_CODE + checkDigits + bban.toString();
	}

	private static String calculateCheckDigits(String countryCode, String bban) {
		String countryNumeric = convertLettersToNumbers(countryCode) + "00";
		String ibanNumeric = bban + countryNumeric;
		BigInteger ibanNumber = new BigInteger(ibanNumeric);
		int checkDigits = 98 - ibanNumber.mod(BigInteger.valueOf(97)).intValue();
		return String.format("%02d", checkDigits);
	}

	private static String convertLettersToNumbers(String letters) {
		StringBuilder result = new StringBuilder();
		for (char c : letters.toCharArray()) {
			result.append(c - 'A' + 10);
		}
		return result.toString();
	}

	public static void main(String[] args) {
		String validIban = generateValidIban();
		System.out.println("Generated Valid IBAN: " + validIban);
	}
}
