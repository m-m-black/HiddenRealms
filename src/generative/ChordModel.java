package generative;

import datastructures.*;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ChordModel {

    private Key key;
    private Mode mode;
    protected HashMap<Degree, Integer> notes; // -- Defines available notes for model --

    public ChordModel() {
        this.key = null;
        this.mode = null;
        this.notes = null;
    }

    public ChordModel(Key key, Mode mode) {
        this.key = key;
        this.mode = mode;
        this.notes = defineNotes();
    }

    // This will determine the MIDI note values of the scale degrees for the model
    private HashMap<Degree, Integer> defineNotes() {
        // Pass in key and mode to get mapping of degrees to integers
        HashMap<Degree, Integer> notes = KeyMapper.getMap(key, ModeMapper.getMap(mode));
        return notes;
    }

    /*
        Returns a list of integers representing MIDI notes
     */
    public abstract int[] nextChord();

    public void setKey(Key key) {
        this.key = key;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setNotes() throws Exception {
        if (key == null || mode == null) {
            // Throw an exception
            throw new Exception();
        } else {
            defineNotes();
        }
    }
}
