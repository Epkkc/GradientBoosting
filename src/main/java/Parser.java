import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Parser {


    private String currentLine;
    private String[] parsed;
    private List<String> keys = new ArrayList<>();
    private List<String> type = new ArrayList<>();
    private List<List<Double>> signals = new ArrayList<>();
    private List<HashMap<String, List<Double>>> output = new ArrayList<>();

    public void parse(String path) throws FileNotFoundException {
        File dataFile = new File(path);
        Scanner scanner = new Scanner(dataFile);
        currentLine = scanner.nextLine();
        parsed = currentLine.split(","); // Получаю наименование всех критериев
        for (String s : parsed) {
            keys.add(s);
            signals.add(new ArrayList<Double>());
        }
        currentLine = scanner.nextLine();
        parsed = currentLine.split(","); // Получаю типы каждого критерия
        for (String s : parsed) {
            type.add(s);
        }

        String[] intermediateParsed;
        while (scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            parsed = currentLine.split(",");
            for (int i = 0; i < parsed.length; i++) {
                if (i == 0){ // Парсинг Daytime (Получение часа)
                    currentLine = parsed[i];
                    intermediateParsed = currentLine.split(" ");
                    intermediateParsed = intermediateParsed[1].split(":");
                    currentLine = intermediateParsed[0];
                    signals.get(i).add(Double.parseDouble(currentLine));
                    System.out.println();
                } else {
                signals.get(i).add(Double.parseDouble(parsed[i]));
                }
            }
        }
        HashMap<String, List<Double>> intermidiateHashMap;
        for (int i=0; i < keys.size(); i++) {
            intermidiateHashMap = new HashMap<>();
            intermidiateHashMap.put(keys.get(i), signals.get(i));
            output.add(intermidiateHashMap);
            intermidiateHashMap = null;
        }
    }


    public List<HashMap<String, List<Double>>> getOutput() {
        return output;
    }

    public List<String> getKeys() {
        return keys;
    }

    public List<String> getType() {
        return type;
    }
}
