/*
    A single row within the sequence,
    initialised with a specific event type, T.
 */

public class Voice<T> {

    private T[] row;

    public Voice(int steps) {
        row = (T[]) new Object[steps];
        initRow();
    }

    /*
        Events, t, are added to the row externally by
        the generative algorithm used, at index i.
     */
    public void addEvent(T t, int i) {
        row[i] = t;
    }

    /*
        When creating a new voice, we make an empty row
        for that voice. Events will be added to the row
        by the generative algorithm used.
     */
    private void initRow() {
        for (int i = 0; i < row.length; i++) {
            row[i] = null; // -- init all row elements to null --
        }
    }

    public T[] getRow() {
        return row;
    }
}
