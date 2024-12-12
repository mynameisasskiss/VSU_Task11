import java.io.*;

public class FileIO {
    public static String readStringFromFile(String fileName) throws IOException {
        String inStr;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            inStr = reader.readLine();
        }
        return inStr;
    }

    public static void writeStringToFile(String fileName, String str) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(str);
        }
    }
}
