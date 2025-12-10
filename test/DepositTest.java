import org.junit.jupiter.api.*;
import java.io.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class DepositTest {

    private final String testFile = "Customer-999.txt";
    private User testUser;

    @BeforeEach
    void setup() throws Exception {

        // Create test user (Customer)
        testUser = new Customer(
                11,             // user_id
                "testuser",     // username
                "Test",         // first name
                "User",         // last name
                "hashed123",    // password
                true,           // isActive
                'C'             // role
        );

        // Create a fresh file for each test
        BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
        writer.write("");
        writer.close();
    }

    @AfterEach
    void cleanup() {
        File f = new File(testFile);
        if (f.exists()) f.delete();
    }

    // ----------------------------------------------------------
    // TEST CalcAccountDeposit()
    // ----------------------------------------------------------

    @Test
    void testCalcAccountDeposit_Today() throws Exception {

        BufferedWriter writer = new BufferedWriter(new FileWriter(testFile, true));

        // Deposit today
        writer.write("1,500," + LocalDateTime.now() + ",Deposit,999\n");

        // Deposit yesterday (ignored)
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

    // ----------------------------------------------------------
    // TEST Depositmoney()
    // ----------------------------------------------------------

    @Test
    void testDepositMoney() throws Exception {

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

        BufferedReader reader = new BufferedReader(new FileReader(testFile));
        String lastLine = "";
        String line;

        while ((line = reader.readLine()) != null)
            lastLine = line;

        reader.close();

        assertTrue(lastLine.contains("Deposit"));
        assertTrue(lastLine.contains(",250.0,"));
        assertTrue(lastLine.endsWith(",1250.0"));
    }
}
