package events;

import java.util.ArrayList;

public class ChordEvent extends Event {

    private ArrayList<Integer> midiNotes;

    public ChordEvent(ArrayList<Integer> midiNotes) {
        this.midiNotes = midiNotes;
    }
}
