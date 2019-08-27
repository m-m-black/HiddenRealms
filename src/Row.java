/*
    A single row within the sequence,
    initialised with a specific event type, T.
 */

public class Row<T> {

    private T[] row;

    public Row(int steps) {
        row = (T[]) new Object[steps];
    }
}
