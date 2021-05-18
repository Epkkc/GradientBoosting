import Trees.Leaf;
import Trees.Node;
import com.sun.javafx.UnmodifiableArrayList;
import dataClasses.Conditions;
import dataClasses.DataClass;
import dataClasses.UnsortedData;

import java.util.*;

public class TreeBuilder {

    private List<Node> roots = new ArrayList<>();
    private double firstPrediction;
    private int numberOfLeaves;
    public void buildForest(UnsortedData unsortedData, int numberOfTrees, int numberOfLeaves, int numberInPack){
        this.numberOfLeaves = numberOfLeaves;
        // Делаю первое предсказание, которое равно среднему арифметическому всех нагрузок
        double sum = 0;
        for (Double aDouble : unsortedData.getSignals().get(unsortedData.getSignals().size() - 1)) {
            sum+=aDouble;
        }
        firstPrediction = sum / unsortedData.getSignals().get(unsortedData.getSignals().size() - 1).size();



        // Создание леса
        for (int i = 0; i < numberOfTrees; i++) {
            //Создание корня
            Node root = new Node();
            root.setLevel(1);
            buildTree(root, unsortedData, numberInPack);

            roots.add(root); // Корень нужно добавить в конце итерации
            System.out.println();
        }
    }



    public Conditions findConditions(UnsortedData unsortedData, int numberInPack){
        String sign;
        Double threshold;

        List<DataClass> sortedData = new ArrayList<>();
        for (int i = 0; i < unsortedData.getKeys().size()-1; i++) {
            sortedData.add(new DataClass());
            sortedData.get(i).setSign(unsortedData.getKeys().get(i));
            sortedData.get(i).setType(unsortedData.getTypes().get(i));
            Collections.copy(unsortedData.getSignals().get(i), sortedData.get(i).getSignValue());
            sortedData.get(i).getSignValue().addAll(unsortedData.getSignals().get(i));
            sortedData.get(i).getResiduals().addAll(unsortedData.getResiduals());
        }

        for (DataClass sortedDatum : sortedData) {
            QuickSort.quickSort(sortedDatum.getSignValue(), 0, sortedDatum.getSignValue().size()-1, sortedDatum.getResiduals() );
        }

        List<HashMap<String, Double>> result = new ArrayList<>();

        for (int i=0; i<sortedData.size(); i++) {
            result.add(SSR.calculateSSR(sortedData.get(i), numberInPack));
        }

        int indexOfMinSSR = 0;
        for (int i = 0; i < result.size() -1; i++) { // Нахождение индекса элемента с наименьшим SSR
            if (result.get(i+1).get("SSR") < result.get(i).get("SSR")){
                indexOfMinSSR = i+1;
            }
        }

        Conditions conditions = new Conditions();
        conditions.setSign(sortedData.get(indexOfMinSSR).getSign());
        conditions.setThreshold(result.get(indexOfMinSSR).get("Threshold"));
        conditions.setSsr(result.get(indexOfMinSSR).get("SSR"));
        return conditions;
    }


    public void calculateResiduals(UnsortedData unsortedData){
        HashMap<String, Double> sample = new HashMap<>();
        unsortedData.getResiduals().clear();
        for (int i = 0; i < unsortedData.getSignals().get(unsortedData.getSignals().size() - 1).size(); i++) {
            for (int j = 0; j < unsortedData.getKeys().size(); j++) { // Составляю sample, чтобы потом получить для него prediction
                sample.put(unsortedData.getKeys().get(j), unsortedData.getSignals().get(j).get(i));

            }

            unsortedData.getResiduals().add(unsortedData.getSignals().get(unsortedData.getSignals().size()-1).get(i) - makePrediction(sample, unsortedData.getKeys()));
        }
    }

    public double makePrediction(HashMap<String, Double> sample, List<String> keys){
        double prediction = firstPrediction;
        if (!roots.isEmpty()){
            for (Node root : roots) {
                prediction += 0.1 * root.proccess(sample, keys);
            }
        }
        return prediction;
    }

    public double getFirstPrediction() {
        return firstPrediction;
    }

    public void buildTree(Node root, UnsortedData unsortedData, int numberInPack){

        double sum = 0;

        Conditions condition;

        calculateResiduals(unsortedData); // Добавил residuals в исходную unsortedData
        condition = findConditions(unsortedData, numberInPack);

        root.setSign(condition.getSign());
        root.setThreshold(condition.getThreshold());
        root.setSsr(condition.getSsr());

        // необходимо разделить первоначальную unsortedData на две, для левого и правого потомков

        UnsortedData leftUnsortedData = new UnsortedData();
        UnsortedData rightUnsortedData = new UnsortedData();

        leftUnsortedData.getKeys().addAll(unsortedData.getKeys());
        leftUnsortedData.getTypes().addAll(unsortedData.getTypes());
        rightUnsortedData.getKeys().addAll(unsortedData.getKeys());
        rightUnsortedData.getTypes().addAll(unsortedData.getTypes());
        for (int a = 0; a < unsortedData.getKeys().size(); a++) {
            leftUnsortedData.getSignals().add(new ArrayList<>());
            rightUnsortedData.getSignals().add(new ArrayList<>());
        }

        int indexOfList = unsortedData.getKeys().indexOf(condition.getSign());
        for (int j = 0; j < unsortedData.getSignals().get(0).size(); j++) {
            if (unsortedData.getSignals().get(indexOfList).get(j) < condition.getThreshold()){
                for (int k = 0; k < unsortedData.getKeys().size(); k++) {
                    leftUnsortedData.getSignals().get(k).add(unsortedData.getSignals().get(k).get(j));

                }
            } else{
                for (int k = 0; k < unsortedData.getKeys().size(); k++) {
                    rightUnsortedData.getSignals().get(k).add(unsortedData.getSignals().get(k).get(j));
                }
            }
        }



        if (root.getLevel() < Math.sqrt(numberOfLeaves) && leftUnsortedData.getSignals().get(0).size() > numberInPack) { // Добавление нодов
            Node leftNode = new Node();
            leftNode.setLevel(root.getLevel() + 1);
            root.setLeftNode(leftNode);
            buildTree(leftNode, leftUnsortedData, numberInPack);
        } else { // Добавление листьев
            calculateResiduals(leftUnsortedData);
            Leaf leftLeaf = new Leaf();
            sum = 0;
            int count = 0;
            for (Double aDouble : leftUnsortedData.getResiduals()) {
                sum += aDouble;
                count++;
            }
            leftLeaf.setValue(sum/count);

            root.setLeftNode(leftLeaf);
        }
        if (root.getLevel() < Math.sqrt(numberOfLeaves) && rightUnsortedData.getSignals().get(0).size() > numberInPack){
            Node rightNode = new Node();
            rightNode.setLevel(root.getLevel()+1);
            root.setRightNode(rightNode);
            buildTree(rightNode, rightUnsortedData, numberInPack);
        } else {
            calculateResiduals(rightUnsortedData);
            Leaf rightLeaf = new Leaf();
            sum = 0;
            int count = 0;
            for (Double aDouble : rightUnsortedData.getResiduals()) {
                sum += aDouble;
                count++;
            }
            rightLeaf.setValue(sum/count);
            root.setRightNode(rightLeaf);
        }
    }
}
