/*
    Receives the sequence and steps through it,
    sending events to the MIDI handler at a specified tempo.
 */

public class Transport {

    private int currentStep;
    private Sequence sequence;
    private double tempo; // -- measured in BPM --
    private MIDIHandler handler;

    public Transport(MIDIHandler handler) {
        this.handler = handler;
        currentStep = 0;
        tempo = 120;
    }

    public void start() {
        /*
            Step through sequence and send events to handler
         */
    }

    public void stop() {
        /*
            Stop stepping through sequence
         */
    }

    private void send(Event e) {
        handler.handle(e);
    }

    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }
}
