package cgb.transfert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cgb.transfert.exception.ExceptionInvalidIbanFormat;
import cgb.transfert.exception.ExceptionInvalidUnCheckableIban;
import cgb.transfert.utils.CGBIbanValidator;
import cgb.transfert.utils.IbanGenerator;

@SpringBootApplication(scanBasePackages = "cgb.transfert")
public class ServerTransferApp {

	public static void main(String[] args) throws ExceptionInvalidIbanFormat, ExceptionInvalidUnCheckableIban {
		SpringApplication.run(ServerTransferApp.class, args);    	
		// TODO Auto-generated method stub	
		//Tester chargement...

		String ibanTest = "FR2254127995913710769626520";
		String ibanTestGenerator = IbanGenerator.generateValidIban();
		System.out.println(ibanTest);
		System.out.println(ibanTestGenerator);
		
		CGBIbanValidator CGBIbanValidatorTest = CGBIbanValidator.getInstanceValidator();
		System.out.println(CGBIbanValidatorTest.isIbanStructureValide(ibanTest));
		System.out.println(CGBIbanValidatorTest.isIbanValide(ibanTest));
		System.out.println(CGBIbanValidatorTest.getCodePays(ibanTest));
		System.out.println(CGBIbanValidatorTest.getChiffreControle(ibanTest));
		System.out.println(CGBIbanValidatorTest.getBBAN(ibanTest));
		
	}
}

