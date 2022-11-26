package ud2.practices.summatrix.solution;

import java.util.List;

public class SumIntegerListThread  extends Thread {
    private List<Integer> listIntegers;
    private int sum;

    public SumIntegerListThread(List<Integer> listIntegers) {
        this.listIntegers = listIntegers;
        sum = 0;
    }

    @Override
    public void run() {
        sum = listIntegers.stream().mapToInt(Integer::intValue).sum();
    }

    public int getSum() {
        return sum;
    }
}
