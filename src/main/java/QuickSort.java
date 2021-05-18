import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuickSort {

    public static void quickSort(List<Double> arr, int from, int to, List<Double> subArr) {
        if (from < to) {

            int divideIndex = partition(arr, from, to, subArr);

            quickSort(arr, from, divideIndex - 1, subArr);

            quickSort(arr, divideIndex, to, subArr);
        }
    }

    private static int partition(List<Double> arr, int from, int to, List<Double> subArr) {
        int rightIndex = to;
        int leftIndex = from;

        double pivot = arr.get(from + (to - from) / 2);
        while (leftIndex <= rightIndex) {

            while (arr.get(leftIndex) < pivot) {
                leftIndex++;
            }

            while (arr.get(rightIndex) > pivot) {
                rightIndex--;
            }

            if (leftIndex <= rightIndex) {
                swap(arr, rightIndex, leftIndex);
                swap(subArr, rightIndex, leftIndex);
                leftIndex++;
                rightIndex--;
            }
        }
        return leftIndex;
    }

    private static void swap(List<Double> arr, int index1, int index2) {
        double tmp  = arr.get(index1);
        arr.set(index1, arr.get(index2));
        arr.set(index2, tmp);
    }

}
