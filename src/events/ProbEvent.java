package events;

import generative.ChordModel;

import java.util.Random;

public class ProbEvent extends ChordEvent {

    Random random = new Random();

    public ProbEvent(ChordModel model) {
        super(model);
    }

    @Override
    public int[] triggerChord() {
        double r = random.nextDouble();
        if (r < 0.5) {
            midiNotes = model.nextChord();
            return midiNotes;
        } else {
            return null;
        }
    }
}
