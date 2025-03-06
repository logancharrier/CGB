

import com.fasterxml.jackson.databind.ObjectMapper;

import cgb.transfert.Account;
import cgb.transfert.TransferController;
import cgb.transfert.TransferRequest;
import cgb.transfert.TransferService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransferController.class) 
public class MonApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Simulation du service TransferService
    @MockBean
    private TransferService transferService;

    @Test
    public void testCreateTransfer() throws Exception {

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setSourceAccountNumber("123456789");
        transferRequest.setDestinationAccountNumber("987654321");
        transferRequest.setAmount(100.0);
        transferRequest.setTransferDate(LocalDate.now());
        transferRequest.setDescription("Virement de test");

        // Simulation d'une réponse du service via MockBean (si nécessaire)
        mockMvc.perform(post("/api/transfers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sourceAccountNumber").value("123456789"))
                .andExpect(jsonPath("$.destinationAccountNumber").value("987654321"))
                .andExpect(jsonPath("$.amount").value(100.0));
    }

    @Test
    public void testGetAllAccounts() throws Exception {
        // Création d'exemples de comptes
        Account account1 = new Account();
        account1.setAccountNumber("123456789");
        account1.setSolde(300.00);

        Account account2 = new Account();
        account2.setAccountNumber("987654321");
        account2.setSolde(500.00);

        Account account3 = new Account();
        account3.setAccountNumber("456789123");
        account3.setSolde(2000.00);

        // Simulation des comptes dans le service via MockBean
        mockMvc.perform(get("/api/transfers/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountNumber").value(account1.getAccountNumber()))
                .andExpect(jsonPath("$[0].solde").value(account1.getSolde()))
                .andExpect(jsonPath("$[1].accountNumber").value(account2.getAccountNumber()))
                .andExpect(jsonPath("$[1].solde").value(account2.getSolde()))
                .andExpect(jsonPath("$[2].accountNumber").value(account3.getAccountNumber()))
                .andExpect(jsonPath("$[2].solde").value(account3.getSolde()));
    }

    @Test
    public void testGetAllAccounts_NoContent() throws Exception {
        mockMvc.perform(get("/api/transfers/accounts"))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Aucun compte bancaire trouvé."));
    }
}
