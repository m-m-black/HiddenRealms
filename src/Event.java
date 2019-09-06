/*
    Atomic unit that represents a musical event.
    An event could be a pitched MIDI note, or an un-pitched MIDI trigger.
 */

public class Event {

    private int midiNote;

    public Event() {

    }

    public Event(int midiNote) {
        this.midiNote = midiNote;
    }

    public int getMidiNote() {
        return midiNote;
    }

    /*
            The event receives an external trigger,
            and performs it's assigned action.
         */
    public void trigger() {

    }
}
