import org.junit.jupiter.api.*;
import java.io.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class DepositTest {

    private final String transactionFile = "Customer-999.txt";
    private final String realUserFile = "Users.txt";
    private final String backupUserFile = "Users-backup.txt";

    private Customer testUser;
    private InputStream originalSystemIn;

    @BeforeEach
    void setup() throws Exception {

        // --- BACKUP REAL Users.txt ---
        File real = new File(realUserFile);
        if (real.exists()) {
            copyFile(realUserFile, backupUserFile);
        }

        // --- CREATE TEST Users.txt ---
        BufferedWriter uw = new BufferedWriter(new FileWriter(realUserFile));
        uw.write("11,testuser,Test,User,hashed123,true,C\n");
        uw.close();

        // --- CREATE TEST TRANSACTION FILE ---
        BufferedWriter tw = new BufferedWriter(new FileWriter(transactionFile));
        tw.write("");
        tw.close();

        // Create test customer
        testUser = new Customer(11, "testuser", "Test", "User", "hashed123", true, 'C');

        originalSystemIn = System.in;
    }

    @AfterEach
    void cleanup() throws Exception {
        // Delete test transaction file
        new File(transactionFile).delete();

        // Restore real Users.txt
        File backup = new File(backupUserFile);
        if (backup.exists()) {
            copyFile(backupUserFile, realUserFile);
            backup.delete();
        }

        System.setIn(originalSystemIn);
    }

    // Utility method to copy files safely
    private void copyFile(String src, String dest) throws IOException {
        try (InputStream in = new FileInputStream(src);
             OutputStream out = new FileOutputStream(dest)) {
            in.transferTo(out);
        }
    }

    @Test
    void testCalcAccountDeposit_Today() throws Exception {
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
    void testDepositMoney() throws Exception {

        // Simulate ENTER key to skip wait
        System.setIn(new ByteArrayInputStream("\n".getBytes()));

        Account acc = new Account();
        acc.setAccount_number("999");
        acc.setBalance(1000);
        acc.setUser_id(11);
        acc.setStatus("active");
        acc.setAccount_type("Savings");
        acc.setOver_draft_count(0);
        acc.setCardType("MASTERCARD");

        Deposit.Depositmoney(acc, 250);

        assertEquals(1250, acc.getBalance());

        BufferedReader reader = new BufferedReader(new FileReader(transactionFile));
        String lastLine = "";
        String line;
        while ((line = reader.readLine()) != null) lastLine = line;
        reader.close();

        assertTrue(lastLine.contains("Deposit"));
        assertTrue(lastLine.contains(",250.0,"));
        assertTrue(lastLine.endsWith(",1250.0"));
    }
}
