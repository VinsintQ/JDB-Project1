import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class FileUpdate {

    public static int findLineByAccountId(String filePath, int id) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            int index = 0;

            while ((line = reader.readLine()) != null) {
                // Split the line by comma
                String[] parts = line.split(",");

                // First value is the ID
                int currentId = Integer.parseInt(parts[0]);

                if (currentId == id) {
                    reader.close();
                    return index;  // return the line number
                }

                index++;
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // If not found
        return -1;
    }




    public static void updateLine(String filePath, int lineNumber, String newContent) {
        try {
            List<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();

            if (lineNumber >= 0 && lineNumber < lines.size()) {
                lines.set(lineNumber, newContent);
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
