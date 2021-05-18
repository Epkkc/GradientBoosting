import dataClasses.DataClass;
import dataClasses.UnsortedData;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {



    public static void main(String[] args) throws FileNotFoundException {

        int numberOfTrees = 100;
        int numberOfLeaves = 512;//
        int numberInPack = 20; //17

        String path = "C:\\Users\\spmn9\\OneDrive\\Рабочий стол\\BoostingData\\DataForBoosting\\NormalData.txt";
        Parser parser = new Parser();

        try {
            parser.parse(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<HashMap<String, List<Double>>> output = parser.getOutput(); // Получил распарсенные сигналы
        List<String> keys = parser.getKeys(); // Получил название сигналов
        List<String> type = parser.getType(); // Получил типы сигналов
        List<DataClass> data = new ArrayList<>();

        // Формирование unsortedData
        UnsortedData uData = new UnsortedData();
        UnsortedData testData = new UnsortedData();

        for (int i = 0; i < keys.size(); i++) {
            uData.getSignals().add(new ArrayList<>());
            uData.getSignals().get(i).addAll(output.get(i).get(keys.get(i)).subList(0, 2000));
            uData.getKeys().add(keys.get(i));
            uData.getTypes().add(type.get(i));
        }
        for (int i = 0; i < keys.size(); i++) {
            testData.getSignals().add(new ArrayList<>());
            testData.getSignals().get(i).addAll(output.get(i).get(keys.get(i)).subList(2001, 4000));
            testData.getKeys().add(keys.get(i));
            testData.getTypes().add(type.get(i));
        }


        List<HashMap<String, Double>> sample = new ArrayList<>();

        double random;
        for (int i = 0; i < 15; i++) {
            random = (int) (Math.random()*500+1001);
            HashMap<String, Double> signal = new HashMap<>();
            for (int j = 0; j < testData.getKeys().size(); j++) {
                signal.put(testData.getKeys().get(j), testData.getSignals().get(j).get((int) random));
            }
            sample.add(signal);

        }
        TreeBuilder builder = new TreeBuilder();
        builder.buildForest(uData, numberOfTrees, numberOfLeaves, numberInPack);



        for (HashMap<String, Double> hashMap : sample) {
            System.out.println("Действительное значение " + hashMap.get("Load") + ", среднее значение " + builder.getFirstPrediction() + ", предсказанное значение " +builder.makePrediction(hashMap, keys)
             + ", Час " + hashMap.get("Datetime") + ", Температура " + hashMap.get("Temperature") + ", Выходной " + hashMap.get("Weekend"));
        }


        System.out.println();
    }
}
