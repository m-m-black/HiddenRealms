package events;/*
    Atomic unit that represents a musical event.
    An event could be a pitched MIDI note, or an un-pitched MIDI trigger.
 */

public class Event {

    protected int midiNote;
    protected int midiChannel;

    public Event() {

    }

    public Event(int midiNote) {
        this.midiNote = midiNote;
    }

    public int getMidiChannel() {
        return midiChannel;
    }

    public void setMidiChannel(int midiChannel) {
        this.midiChannel = midiChannel;
    }

    /*
            The event receives an external trigger,
            and performs it's assigned action.
         */
    public int trigger() {
        return midiNote;
    }
}
