package events;/*
    Atomic unit that represents a musical event.
    An event could be a pitched MIDI note, or an un-pitched MIDI trigger.
 */

public class Event {

    protected int midiNote;
    protected int midiChannel;
    protected int velocity;

    public Event() {

    }

    public Event(int midiNote) {
        this.midiNote = midiNote;
        this.velocity = 100;
    }

    public int getMidiChannel() {
        return midiChannel;
    }

    public void setMidiChannel(int midiChannel) {
        this.midiChannel = midiChannel;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getVelocity() {
        return velocity;
    }

    /*
                The event receives an external trigger,
                and performs it's assigned action.
             */
    public int trigger() {
        return midiNote;
    }
}
