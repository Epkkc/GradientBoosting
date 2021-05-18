package Trees;

import java.util.HashMap;
import java.util.List;

public class Node {

    private String sign;
    private double threshold;
    private Node leftNode;
    private Node rightNode;
    private int level;
    private double ssr;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getSsr() {
        return ssr;
    }

    public void setSsr(double ssr) {
        this.ssr = ssr;
    }

    public double proccess(HashMap<String, Double> sample, List<String> keys){

        int index = keys.indexOf(sign);
        double value = sample.get(sign);

        if (value < threshold){
            return leftNode.proccess(sample, keys);
        } else{
            return rightNode.proccess(sample, keys);
        }
    }

}
