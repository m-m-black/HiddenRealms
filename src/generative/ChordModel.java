package generative;

import datastructures.Mode;

import java.util.ArrayList;

public abstract class ChordModel {

    private Mode mode; // -- This defines the available notes for the chord model --

    public ChordModel() {
        this.mode = null;
    }

    public ChordModel(Mode mode) {
        this.mode = mode;
    }

    /*
        Returns a list of integers representing MIDI notes
     */
    public abstract ArrayList<Integer> nextChord();

    public void setMode(Mode mode) {
        this.mode = mode;
    }
}
