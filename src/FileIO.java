import java.io.*;
import java.util.stream.Collectors;

public class FileIO {
    public static String readStringFromFile(String fileName) throws IOException {
        String inStr;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            inStr = reader.lines().collect(Collectors.joining("\n")).toString();
        }
        return inStr;
    }

    public static void writeStringToFile(String fileName, String str) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(str);
        }
    }
}
