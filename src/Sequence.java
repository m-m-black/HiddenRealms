/*
    Stores the score data for the sequence,
    as a list of voices, where each voice is a row.
 */

import java.util.ArrayList;

public class Sequence {

    private ArrayList<Row> rows; // -- List of rows in the sequence --
    private int steps; // -- Length of the sequence, in steps --

    public Sequence(int steps) {
        this.steps = steps;
        rows = new ArrayList<>();
    }

    public void addRow(Row row) {
        rows.add(row);
    }

    public int getSteps() {
        return steps;
    }
}
