package events;

import generative.ChordModel;
import processing.core.PApplet;

import java.util.Random;

public class ProbEvent extends ChordEvent {

    /*
        The noise space is defined when this ProbEvent object is created.
        This space is local to this object, as is the noise offset value.
     */
    private Random random = new Random();
    private PApplet pApplet = new PApplet();
    private int noiseOffset = 0;

    public ProbEvent(ChordModel model) {
        super(model);
    }

    @Override
    public int[] triggerChord() {
        double n = pApplet.noise(noiseOffset);
        noiseOffset++;
        double r = random.nextDouble();
        if (r < n) { // -- Probability of chord based on Perlin noise value --
            midiNotes = model.nextChord();
            return midiNotes;
        } else {
            return null;
        }
    }
}
