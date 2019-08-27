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
        currentStep = 0;
        tempo = 120;
        this.handler = handler;
    }

    public void start() {}

    public void stop() {}

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
