import dataClasses.DataClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SSR {




    public static HashMap<String, Double> calculateSSR(DataClass data, int numberInPack){
        double threshold = 0;
        double averageLow = 0;
        double averageHigh = 0;
        int numbersLow = 0;
        int numbersHigh = 0;
        double average;
        double ssr = 0;
        HashMap<String, Double> output = new HashMap<>();
        List<Double> ranks = new ArrayList<>();
        if (data.getType().equals("Boolean")){

            for (Double aDouble : data.getSignValue()) {
                if (!ranks.contains(aDouble)) {
                    ranks.add(aDouble);
                }
            }
            if (ranks.size() ==1){
                output.put("SSR", Double.MAX_VALUE);
                output.put("Threshold", threshold);
                return output;
            }

            threshold = 0.5;

            //Здесь можно ускорить, если знаешь где граница раздела между нулями и единицами
            for (int i = 0; i < data.getSignValue().size(); i++) {
                if (data.getSignValue().get(i)< threshold){
                    averageLow += data.getResiduals().get(i);
                    numbersLow++;
                } else {
                    averageHigh += data.getResiduals().get(i);
                    numbersHigh++;
                }
            }
            averageLow /= numbersLow;
            averageHigh /= numbersHigh;
            // Расчёт ssr
            for (int i = 0; i < data.getSignValue().size(); i++) {
                if (data.getSignValue().get(i)< threshold){
                    average = averageLow;
                } else{
                    average = averageHigh;
                }
                ssr += Math.pow((data.getResiduals().get(i)-average),2);

            }
            output.put("SSR", ssr);
            output.put("Threshold", threshold);

        } else if (data.getType().equals("Ranked") || data.getType().equals("Numeric")){


            List<Double> SSRs = new ArrayList<>();
            List<Double> tresholds = new ArrayList<>();

            if (data.getType().equals("Ranked")) {
                for (Double aDouble : data.getSignValue()) {
                    if (!ranks.contains(aDouble)) {
                        ranks.add(aDouble);
                    }
                }

            }
            if (data.getType().equals("Numeric")) {
                int sum = 0;
                averageLow = 0;
                averageHigh = 0;
                numbersLow = 0;
                numbersHigh = 0;
                ssr = 0;

                for (int i = 0; i < data.getSignValue().size()/numberInPack; i++) {
                    ranks.add(data.getSignValue().get((i+1)*numberInPack-1));
                }
                ranks.add(data.getSignValue().get(data.getSignValue().size()-1)); // Необходимо добавить последний элемент (он не играет никакой роли, так как последующий цикл обрезает последние элементы массива)
            }

            if (ranks.size() == 1){
                output.put("SSR", Double.MAX_VALUE);
                output.put("Threshold", threshold);
                return output;
            }

            for (int i = 0; i < ranks.size()-1; i++) {

                if (data.getType().equals("Ranked")) {
                    threshold = (ranks.get(i) + ranks.get(i + 1)) / 2;
                } else if (data.getType().equals("Numeric")) {
                    threshold = ranks.get(i);
                }


                tresholds.add(threshold);



                for (int j = 0; j < data.getSignValue().size(); j++) {
                    if (data.getSignValue().get(j) < threshold) {
                        averageLow += data.getResiduals().get(j);
                        numbersLow++;
                    } else {
                        averageHigh += data.getResiduals().get(j);
                        numbersHigh++;
                    }
                }
                averageLow /= numbersLow;
                averageHigh /= numbersHigh;
                for (int h = 0; h < data.getSignValue().size(); h++) {
                    if (data.getSignValue().get(h)< threshold){
                        average = averageLow;
                    } else{
                        average = averageHigh;
                    }
                    ssr += Math.pow((data.getResiduals().get(h)-average),2);
                }

                SSRs.add(ssr);
            }

            // Нахожу минимальный SSR
            int indexOfMinSSR = 0;
            for (int i = 0; i < SSRs.size()-1; i++) {
                if (SSRs.get(i) < SSRs.get(i+1)){
                    indexOfMinSSR = i;
                }
            }


            if (SSRs.size() == 0){
                System.out.println(data.getSign() + " " + data.getSignValue());
            }


            output.put("SSR", SSRs.get(indexOfMinSSR));
            output.put("Threshold",tresholds.get(indexOfMinSSR));


        }
//        System.out.println(output.get("SSR") + ", " + output.get("Threshold") + ", " + data.getSign());
        return output;
    }


}
