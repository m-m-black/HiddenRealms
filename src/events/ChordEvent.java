package events;

import generative.ChordModel;

public class ChordEvent extends Event {

    private int[] midiNotes;
    private ChordModel model;

    public ChordEvent(ChordModel model) {
        this.model = model;
    }

    public int[] triggerChord() {
        midiNotes = model.nextChord();
        return midiNotes;
    }
}
