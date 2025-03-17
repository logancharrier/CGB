package cgb.transfert;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user")
public class TransferControllerTest {

	@Autowired
	private MockMvc mockMvc;

//	@Test
//	public void testGetAllAccounts() throws Exception {
//		String expectedJson = """
//								[
//				    {
//				        "accountNumber": "FR5760982407132967990639385",
//				        "solde": 6538.04
//				    },
//				    {
//				        "accountNumber": "FR7604696636729114405492739",
//				        "solde": 6558.06
//				    },
//				    {
//				        "accountNumber": "FR9729909834896869096735332",
//				        "solde": 1739.04
//				    },
//				    {
//				        "accountNumber": "FR0632715952607668316488249",
//				        "solde": 8077.06
//				    },
//				    {
//				        "accountNumber": "FR4735270598232487977132268",
//				        "solde": 7551.27
//				    },
//				    {
//				        "accountNumber": "FR5355775216413690788080605",
//				        "solde": 158.52
//				    },
//				    {
//				        "accountNumber": "FR9358266117025620746403826",
//				        "solde": 4649.04
//				    },
//				    {
//				        "accountNumber": "FR0991223393416416261797802",
//				        "solde": 9934.23
//				    },
//				    {
//				        "accountNumber": "FR9245632999154568046032569",
//				        "solde": 1673.0
//				    },
//				    {
//				        "accountNumber": "FR8085890563927157264653046",
//				        "solde": 566.76
//				    },
//				    {
//				        "accountNumber": "FR4943798471467508485109991",
//				        "solde": 5835.98
//				    },
//				    {
//				        "accountNumber": "FR1373263297870555864142079",
//				        "solde": 778.97
//				    },
//				    {
//				        "accountNumber": "FR5719052561060864244917996",
//				        "solde": 259.75
//				    },
//				    {
//				        "accountNumber": "FR7911611368506660437137694",
//				        "solde": 8429.98
//				    },
//				    {
//				        "accountNumber": "FR3527157921784458116724313",
//				        "solde": 8407.87
//				    },
//				    {
//				        "accountNumber": "FR4169077815563736966121871",
//				        "solde": 5842.79
//				    },
//				    {
//				        "accountNumber": "FR3631975624124384163142897",
//				        "solde": 5314.77
//				    },
//				    {
//				        "accountNumber": "FR0787580668366799126094013",
//				        "solde": 3337.51
//				    },
//				    {
//				        "accountNumber": "FR9515382821808585912593913",
//				        "solde": 96.85
//				    },
//				    {
//				        "accountNumber": "FR4591018281365837447142986",
//				        "solde": 9967.07
//				    }
//				]
//								""";
//
//		mockMvc.perform(get("/api/transfers/accounts").contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk()).andExpect(content().json(expectedJson));
//	}

	@Test
	public void testObtenirUtilisateur() throws Exception {
		int id = 1;
		mockMvc.perform(get("/test/{id}", id)).andExpect(status().isOk())
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

		mockMvc.perform(post("/api/transfers").contentType(MediaType.APPLICATION_JSON).content(transferRequestJson))
				.andExpect(status().isOk()).andExpect(content().json(expectedResponseJson));
	}
}
