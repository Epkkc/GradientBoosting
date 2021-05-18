package dataClasses;

import java.util.ArrayList;
import java.util.List;

public class UnsortedData {
    private List<List<Double>> signals = new ArrayList<>();
    private List<String> keys = new ArrayList<>();
    private List<String> types = new ArrayList<>();
    private List<Double> residuals = new ArrayList<>();

    public List<List<Double>> getSignals() {
        return signals;
    }

    public void setSignals(List<List<Double>> signals) {
        this.signals = signals;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<Double> getResiduals() {
        return residuals;
    }

    public void setResiduals(List<Double> residuals) {
        this.residuals = residuals;
    }
}
