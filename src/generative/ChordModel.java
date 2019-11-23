package generative;

import datastructures.Degree;
import datastructures.Key;
import datastructures.KeyMapper;
import datastructures.Mode;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ChordModel {

    private Key key;
    private Mode mode;
    private HashMap<Degree, Integer> notes; // -- Defines available notes for model --

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

    private HashMap<Degree, Integer> defineNotes() {
        // Pass in key and mode to get mapping of degrees to integers
        HashMap<Degree, Integer> notes = KeyMapper.getMap(key, mode);
        return notes;
    }

    /*
        Returns a list of integers representing MIDI notes
     */
    public abstract ArrayList<Integer> nextChord();

    public void setMode(Mode mode) {
        this.mode = mode;
    }
}
