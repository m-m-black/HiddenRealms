/*
    A single row within the sequence,
    initialised with the parent Event type.
 */

public class Voice {

    private Event[] row;

    public Voice(int steps) {
        row = new Event[steps];
        initRow();
    }

    /*
        Events, e, are added to the row externally by
        the generative algorithm used, at index i.
     */
    public void addEvent(Event e, int i) {
        row[i] = e;
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

    public Event[] getRow() {
        return row;
    }
}
