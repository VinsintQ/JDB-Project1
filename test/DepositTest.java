import org.junit.jupiter.api.*;
import java.io.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class DepositTest {

    private final String testFile = "Customer-999.txt";

    @BeforeEach
    void setup() throws Exception {
        // Create a fresh file for each test
        BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
        writer.write(""); // empty file
        writer.close();
    }

    @AfterEach
    void cleanup() {
        // Delete temp file
        File f = new File(testFile);
        if (f.exists()) f.delete();
    }

    // ----------------------------------------------------------
    // TEST CalcAccountDeposit()
    // ----------------------------------------------------------

    @Test
    void testCalcAccountDeposit_Today() throws Exception {

        BufferedWriter writer = new BufferedWriter(new FileWriter(testFile, true));

        // A deposit today
        writer.write("1,500," + LocalDateTime.now() + ",Deposit,999\n");

        // A deposit NOT today (yesterday)
        writer.write("2,600," + LocalDateTime.now().minusDays(1) + ",Deposit,999\n");

        writer.close();

        double total = Deposit.CalcAccountDeposit("999");

        // Should only count today's 500
        assertEquals(500, total);
    }

    @Test
    void testCalcAccountDeposit_NoDeposits() {
        double total = Deposit.CalcAccountDeposit("999");

        assertEquals(0, total);
    }


    // ----------------------------------------------------------
    // TEST Depositmoney()
    // ----------------------------------------------------------

    @Test
    void testDepositMoney() throws Exception {

        // Mock account
        Account acc = new Account();
        acc.setAccount_number("999");
        acc.setBalance(1000);
        acc.setUser_id(11);
        acc.setStatus("active");
        acc.setAccount_type("Savings");
        acc.setOver_draft_count(0);
        acc.setCardType("MASTERCARD");

        // Run deposit
        Deposit.Depositmoney(acc, 250);

        // New balance must be 1250
        assertEquals(1250, acc.getBalance(), 0.0001);

        // Check file content
        BufferedReader reader = new BufferedReader(new FileReader(testFile));
        String lastLine = "";
        String line;

        while ((line = reader.readLine()) != null)
            lastLine = line;

        reader.close();

        // Last line should contain: id, amount, date, Deposit, 999, finalBalance
        assertTrue(lastLine.contains("Deposit"));
        assertTrue(lastLine.contains(",250.0,"));
        assertTrue(lastLine.endsWith(",1250.0"));
    }

}
