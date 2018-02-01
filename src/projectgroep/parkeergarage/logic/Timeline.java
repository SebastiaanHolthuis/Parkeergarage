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
    public List<Snapshot> past = new ArrayList<>();
    public List<Snapshot> future = new ArrayList<>();
    int maxSize;

    Timeline(int maxSize) {
        this.maxSize = maxSize;
    }


    /**
     * Actions
     */
    Snapshot backwards(int steps) {
        if (cursor() == 0)
            return null;

        future = new ArrayList<Snapshot>() {{
            addAll(past.subList(remainingPast(steps), past.size()));
        }};

        past = past.subList(0, remainingPast(steps) + 1);

        return atCursor();
    }

    int remainingPast(int steps) {
        return (past.size() - steps) > 0 ? (past.size() - steps) : 0;
    }

    Snapshot forwards(int steps) {
        if (future.size() == 0)
            return null;

        IntStream.range(0, steps).forEach(i -> stepForward());

        return atCursor();
    }

    void stepForward() {
        past.add(future.get(0));
        future.remove(0);
    }

    void saveSnapshot(Snapshot sn) {
        past.addAll(future);
        past.add(sn);
        future.clear();
    }


    /**
     * Getters
     */
    Snapshot atCursor() {
        return past.get(cursor());
    }

    int cursor() {
        return past.size() - 1;
    }

    Collection<Snapshot> all() {
        return new ArrayList<Snapshot>() {{
            addAll(past);
            addAll(future);
        }};
    }

    int size() {
        return past.size() + future.size();
    }


    boolean isFull() {
        return size() == maxSize;
    }

    /**
     * Utils
     */
    public int stepsBack() {
        return past.size();
    }
}
