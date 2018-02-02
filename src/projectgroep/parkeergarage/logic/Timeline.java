package projectgroep.parkeergarage.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 * first [past] last - cursor - first [future] last
 */
public class Timeline {
    public List<Snapshot> snapshots = new ArrayList<>();
    int cursor = 0;
    int maxSize;

    Timeline(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * Actions
     */
    Snapshot backwards(int steps) {
        cursor = Math.max(cursor - steps, 0);
        return atCursor();
    }

    Snapshot forwards(int steps) {
        cursor = Math.min(cursor + steps, snapshots.size() - 1);
        return atCursor();
    }

    void saveSnapshot(Snapshot sn) {
        snapshots.add(sn);
        cursor = snapshots.size() - 1;
    }

    /**
     * Getters
     */
    Snapshot atCursor() {
        return snapshots.get(cursor);
    }

    public int getCursor() {
        return cursor;
    }


    public int size() {
        return snapshots.size();
    }


    boolean isFull() {
        return size() == maxSize;
    }

    boolean canForward() {
        return cursor < snapshots.size() - 1;
    }

    public int stepsBack() {
        return cursor;
    }
}
