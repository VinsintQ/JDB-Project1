import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class FileUpdate {


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
