package magus.util;

/**
 * Pair data structure to store 2 values
 *
 * @param <T1> Data type of first value
 * @param <T2> Data type of second value
 */
public class Pair<T1, T2> {
    public final T1 first;
    public final T2 second;

    /**
     * Constructor for Pair data structure
     *
     * @param first First value of pair
     * @param second Second value of pair
     */
    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return String.format("{first=%s, second=%s}", first.toString(), second.toString());
    }
}
