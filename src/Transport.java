/*
    Receives the sequence and steps through it,
    sending events to the MIDI handler at a specified tempo.
 */

public class Transport {

    private int currentStep;
    private double tempo; // -- measured in BPM --

    public Transport() {
        currentStep = 0;
        tempo = 120;
    }

    public void start() {}

    public void stop() {}

    public void setTempo(double tempo) {
        this.tempo = tempo;
    }
}
