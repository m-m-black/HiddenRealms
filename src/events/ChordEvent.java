package events;

import generative.ChordModel;

public class ChordEvent extends Event {

    protected int[] midiNotes;
    protected ChordModel model;

    public ChordEvent(ChordModel model) {
        this.model = model;
    }

    public int[] triggerChord() {
        midiNotes = model.nextChord();
        return midiNotes;
    }
}
