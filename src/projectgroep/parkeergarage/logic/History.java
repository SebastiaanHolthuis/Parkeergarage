package projectgroep.parkeergarage.logic;

import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

public class History {
    Stack<Snapshot> snapshots = new Stack<>();
    int maxSize;

    History(int maxSize) {
        this.maxSize = maxSize;
    }

    Snapshot getStepsBack(int steps) {
        if (snapshots.size() == 0)
            return null;

        IntStream.range(0, steps).forEach(i -> {
            if (snapshots.size() > 1) snapshots.pop();
        });

        return snapshots.peek();
    }

    void saveSnapshot(Snapshot sn) {
        if (isFull())
            popBottom();

        snapshots.push(sn);
    }

    boolean isFull() {
        return snapshots.size() == maxSize;
    }

    void popBottom() {
        snapshots.remove(0);
    }

    public int stepsBack() {
        return snapshots.size();
    }
}
