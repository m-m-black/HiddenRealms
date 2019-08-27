/*
    A single row within the sequence,
    initialised with a specific event type, T.
 */

public class Voice<T> {

    private T[] row;

    public Voice(int steps) {
        row = (T[]) new Object[steps];
    }

    public T[] getRow() {
        return row;
    }
}
