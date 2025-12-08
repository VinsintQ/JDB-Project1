import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUpdate {
    public static int findLineByAccountId(String filePath, int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;
            int index = 0; // 0-based line index

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    index++;
                    continue;
                }

                String[] parts = line.split(",");

                int currentId = Integer.parseInt(parts[0].trim());

                if (currentId == id) {
                    return index;
                }

                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1; // Not found
    }
    public static void createFileIfNotExist(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                boolean created = file.createNewFile();
            } catch (IOException e) {
                System.out.println("An error occurred while creating the file.");
                e.printStackTrace();
            }
        } else {

        }
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
