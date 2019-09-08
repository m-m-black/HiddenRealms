/*
    Atomic unit that represents a musical event.
    An event could be a pitched MIDI note, or an un-pitched MIDI trigger.
 */

public class Event {

    protected int midiNote;

    public Event() {

    }

    public Event(int midiNote) {
        this.midiNote = midiNote;
    }

    /*
            The event receives an external trigger,
            and performs it's assigned action.
         */
    public int trigger() {
        return midiNote;
    }
}
