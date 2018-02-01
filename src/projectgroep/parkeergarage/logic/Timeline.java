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

        Snapshot toReturn = past.get(remainingPast(steps) - 1);

        past = past.subList(0, remainingPast(steps));

        return toReturn;
    }

    int remainingPast(int steps) {
        return (past.size() - steps) > 0 ? (past.size() - steps) : 0;
    }

    Snapshot forwards(int steps) {
        past = new ArrayList<Snapshot>() {{
            addAll(past);
            addAll(future.subList(0, steps));
        }};

        future = future.subList(steps, future.size());

        return atCursor();
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

    boolean canForward() {
        return !future.isEmpty();
    }

    /**
     * Utils
     */
    public int stepsBack() {
        return past.size();
    }
}
