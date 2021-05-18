package dataClasses;

import java.util.ArrayList;
import java.util.List;

public class DataClass {

    private List<Double> signValue = new ArrayList<>();
    private List<Double> residuals = new ArrayList<>();
    private String sign;
    private String type;

    public List<Double> getSignValue() {
        return signValue;
    }

    public void setSignValue(List<Double> signValue) {
        this.signValue = signValue;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Double> getResiduals() {
        return residuals;
    }

    public void setResiduals(List<Double> residuals) {
        this.residuals = residuals;
    }
}
