import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Format {
    public static String format(String str, int lineLen) {
        String[] strArr = str.split("\n");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < strArr.length; i++) {
            if (!strArr[i].startsWith(" ")) {
                if (strArr[i].length() > lineLen) {
                    int lastLen = 0;
                    for (int j = 0; j < strArr[i].length() / lineLen; j++) {
                        if (j == 0) {
                            list.add(" ".repeat(6) + strArr[i].substring(lastLen,lineLen*(j+1)));
                        }
                        {
                            list.add(strArr[i].substring(lastLen, lineLen * (j + 1)));
                            lastLen += lineLen;
                        }
                    }
                    list.add(strArr[i].substring(lastLen, strArr[i].length()));
                }else{
                    list.add(" ".repeat(6) + strArr[i]);
                }
            }
        }
        System.out.println(list.stream().collect(Collectors.joining("\n")));
        return list.stream().collect(Collectors.joining("\n"));
    }

}
