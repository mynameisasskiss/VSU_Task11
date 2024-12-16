import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Format {

    private static List<String> addBlankLines(List<String> formattedLine, int lineLen) {
        if(formattedLine.size() == 1) return formattedLine;
        String strng = formattedLine.stream().map(wrd -> String.join("", wrd)).collect(Collectors.joining());
        int len = strng.length();
        //System.out.println(len);
        while (len < lineLen) {
            for (int j = 0; j < formattedLine.size(); j++) {
                if (len < lineLen) {
                    if (formattedLine.get(j).endsWith(" ")) {
                        formattedLine.set(j, formattedLine.get(j) + " ");
                        len += 1;
                        //System.out.println(len);
                    }
                }
            }
        }
        return formattedLine;
    }

    private static List<String> removeLastBlank(List<String> formattedLine) {
        int len = formattedLine.size();
        formattedLine.set(len - 1, formattedLine.get(len - 1).substring(0, formattedLine.get(len - 1).length() - 1));
        return formattedLine;
    }

    public static String format(String str, int lineLen) {
        List<List<String>> formattedText = new ArrayList<>();
        String[] lines = str.split("\n");

        for (String line : lines) {
            List<String> words = new ArrayList<>(Arrays.asList(line.split(" ")));
            words.add("костыль");
            List<String> formattedLine = new ArrayList<>();
            int currentLength = 0;
            formattedLine.add(" ".repeat(6));

            for (int i = 0; i < words.size() - 1; i++) {
                String word = words.get(i);
                String nextWord = words.get(i + 1);
                if (i == 0) {
                    currentLength = 6; // Учитываем длину табуляции для первого слова
                }
                int nextWordLength = nextWord.length();
                if (currentLength + word.length() + 1 + nextWordLength <= lineLen) {
                    formattedLine.add(word + " ");
                    currentLength += word.length() + 1;
                } else {
                    formattedText.add(addBlankLines(removeLastBlank(formattedLine),lineLen));
                    formattedLine = new ArrayList<>();
                    currentLength = 0;
                    i--; // Возвращаемся к предыдущему слову
                }
            }
            // Добавляем последнюю строку
            if (!formattedLine.isEmpty()) {
                formattedText.add(removeLastBlank(formattedLine));
                //formattedText.add(addBlankLines(removeLastBlank(formattedLine,lineLen)));
            }
        }

        return formattedText.stream()
                .map(line -> String.join("", line))
                .collect(Collectors.joining("\n"));
    }
}