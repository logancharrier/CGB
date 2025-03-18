package cgb.transfert;

import cgb.transfert.exception.ExceptionInvalidIbanFormat;
import cgb.transfert.exception.ExceptionInvalidUnCheckableIban;
import cgb.transfert.utils.CGBIbanValidator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user")
public class TransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final CGBIbanValidator ibanValidator = CGBIbanValidator.getInstanceValidator();

    @Test
    public void testObtenirUtilisateur() throws Exception {
        int id = 1;
        mockMvc.perform(get("/test/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Recu : " + id));
    }

    @Test
    public void testCreateTransfer() throws Exception {
        String transferRequestJson = """
                {
                    "sourceAccountNumber": "FR8531119080966452723032312",
                    "destinationAccountNumber": "FR2239311197635473402152655",
                    "amount": 100.0,
                    "transferDate": "2025-03-13",
                    "description": "Virement de test"
                }
                """;

        String expectedResponseJson = """
                {
                    "sourceAccountNumber": "FR8531119080966452723032312",
                    "destinationAccountNumber": "FR2239311197635473402152655",
                    "amount": 100.0,
                    "transferDate": "2025-03-13",
                    "description": "Virement de test"
                }
                """;

        mockMvc.perform(post("/api/transfers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transferRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }

    @Test
    public void testIbanFormatInvalide() {
        String invalidIban = "XX7630004015870002601171220"; 

        Exception exception = assertThrows(ExceptionInvalidIbanFormat.class, () -> {
            ibanValidator.isIbanStructureValide(invalidIban);
        });

        assertTrue(exception.getMessage().contains("Format IBAN invalide"));
    }

    @Test
    public void testIbanAvecCodeVerificationIncorrect() {
        String ibanInvalidCRC = "FR7611112222333344445555666"; 

        Exception exception = assertThrows(ExceptionInvalidUnCheckableIban.class, () -> {
            ibanValidator.isIbanValide(ibanInvalidCRC);
        });

        assertTrue(exception.getMessage().contains("IBAN non vérifiable ou invalide"));
    }

    @Test
    public void testIbanValide() throws ExceptionInvalidIbanFormat, ExceptionInvalidUnCheckableIban {
        String validIban = "FR7630004015870002601171220"; // IBAN correct

        boolean structureValide = ibanValidator.isIbanStructureValide(validIban);
        boolean ibanValide = ibanValidator.isIbanValide(validIban);

        assertTrue(structureValide);
        assertTrue(ibanValide);
    }
}
