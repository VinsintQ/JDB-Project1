import org.junit.jupiter.api.*;
import java.io.*;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class DepositTest {

    private final String transactionFile = "Customer-999.txt";
    private final String userFile = "Users.txt";  // assuming User.getUser reads from here

    @BeforeEach
    void setup() throws Exception {

        BufferedWriter userWriter = new BufferedWriter(new FileWriter(userFile));
        userWriter.write("11,testuser,email@example.com,x,x,x,C\n");
        userWriter.close();


        BufferedWriter writer = new BufferedWriter(new FileWriter(transactionFile));
        writer.write("");
        writer.close();
    }

    @AfterEach
    void cleanup() {
        new File(transactionFile).delete();
        new File(userFile).delete();
    }

    @Test
    void testCalcAccountDeposit_Today() throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(transactionFile, true));

        writer.write("1,500," + LocalDateTime.now() + ",Deposit,999\n");
        writer.write("2,600," + LocalDateTime.now().minusDays(1) + ",Deposit,999\n");

        writer.close();


        User dummyUser =new Customer(
                11,
                "testuser",
                "Test",
                "User",
                "hashed123",
                true,
                'C'
        );
        double total = Deposit.CalcAccountDeposit("999", dummyUser);

        assertEquals(500, total);
    }

    @Test
    void testCalcAccountDeposit_NoDeposits() {
        User dummyUser =new Customer(
                11,
                "testuser",
                "Test",
                "User",
                "hashed123",
                true,
                'C'
        );
        double total = Deposit.CalcAccountDeposit("999", dummyUser);
        assertEquals(0, total);
    }

    @Test
    void testDepositMoney() throws IOException {
        // Prepare simulated input: just press Enter
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

        BufferedReader reader = new BufferedReader(new FileReader("Customer-999.txt"));
        String lastLine = "", line;
        while ((line = reader.readLine()) != null) lastLine = line;
        reader.close();

        assertTrue(lastLine.contains("Deposit"));
        assertTrue(lastLine.contains(",250.0,"));
        assertTrue(lastLine.endsWith(",1250.0"));
    }
}
