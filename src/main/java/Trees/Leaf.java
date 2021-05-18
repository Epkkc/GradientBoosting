package Trees;

import java.util.HashMap;
import java.util.List;

public class Leaf extends Node{

    private double value;

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public double proccess(HashMap<String, Double> sample, List<String> keys) {
        return value;
    }
}
