import org.junit.jupiter.api.*;
import java.io.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class DepositTest {

    private final String transactionFile = "Customer-999.txt";
    private final String userFile = "Users.txt";  // your real user file — won't delete this!
    private Customer testUser;
    private final InputStream systemIn = System.in;

    @BeforeEach
    void setup() throws IOException {
        // Create Users.txt with test user info (overwrite existing file)
        BufferedWriter userWriter = new BufferedWriter(new FileWriter(userFile));
        userWriter.write("11,testuser,Test,User,hashed123,true,C\n");
        userWriter.close();

        // Create empty transaction file
        BufferedWriter writer = new BufferedWriter(new FileWriter(transactionFile));
        writer.write("");
        writer.close();

        // Create Customer instance
        testUser = new Customer(
                11,
                "testuser",
                "Test",
                "User",
                "hashed123",
                true,
                'C'
        );
    }

    @AfterEach
    void cleanup() {
        // Only delete the transaction test file, NOT the real Users.txt file
        new File(transactionFile).delete();

        // Restore original System.in
        System.setIn(systemIn);
    }

    @Test
    void testCalcAccountDeposit_Today() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(transactionFile, true));

        writer.write("1,500," + LocalDateTime.now() + ",Deposit,999\n");
        writer.write("2,600," + LocalDateTime.now().minusDays(1) + ",Deposit,999\n");

        writer.close();

        double total = Deposit.CalcAccountDeposit("999", testUser);

        assertEquals(500, total);
    }

    @Test
    void testCalcAccountDeposit_NoDeposits() {
        double total = Deposit.CalcAccountDeposit("999", testUser);
        assertEquals(0, total);
    }

    @Test
    void testDepositMoney() throws IOException {
        // Simulate user pressing Enter (to avoid blocking on Scanner input)
        String simulatedInput = "\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Account acc = new Account();
        acc.setAccount_number("999");
        acc.setBalance(1000);
        acc.setUser_id(11);
        acc.setStatus("active");
        acc.setAccount_type("Savings");
        acc.setOver_draft_count(0);
        acc.setCardType("MASTERCARD");

        Deposit.Depositmoney(acc, 250);

        assertEquals(1250, acc.getBalance(), 0.0001);

        BufferedReader reader = new BufferedReader(new FileReader(transactionFile));
        String lastLine = "", line;
        while ((line = reader.readLine()) != null) lastLine = line;
        reader.close();

        assertTrue(lastLine.contains("Deposit"));
        assertTrue(lastLine.contains(",250.0,"));
        assertTrue(lastLine.endsWith(",1250.0"));
    }
}
