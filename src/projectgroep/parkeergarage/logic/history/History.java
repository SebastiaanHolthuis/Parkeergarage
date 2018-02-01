package projectgroep.parkeergarage.logic.history;

import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

public class History {
    public Stack<Snapshot> snapshots = new Stack<>();
    int maxSize;

    public History(int maxSize) {
        this.maxSize = maxSize;
    }

    public Snapshot getStepsBack(int steps) {
        if (snapshots.size() == 0)
            return null;

        IntStream.range(0, steps).forEach(i -> {
            if (snapshots.size() > 1) snapshots.pop();
        });

        return snapshots.peek();
    }

    public void saveSnapshot(Snapshot sn) {
//        System.out.println(snapshots.size());
        if (isFull())
            popBottom();

        snapshots.push(sn);
    }

    public boolean isFull() {
        return snapshots.size() == maxSize;
    }

    public void popBottom() {
        snapshots.remove(0);
    }

    public int stepsBack() {
        return snapshots.size();
    }
}
