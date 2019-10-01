package events;

import datastructures.Degree;
import datastructures.Mode;
import datastructures.ModeMapper;
import generative.MarkovMatrix;

public class MarkovEvent extends Event {

    private MarkovMatrix markovMatrix;
    private Mode mode;

    public MarkovEvent(MarkovMatrix markovMatrix, Mode mode) {
        this.markovMatrix = markovMatrix;
        this.mode = mode;
    }

    public int trigger() {
        Degree degree = markovMatrix.getNextNote();
        midiNote = ModeMapper.getMap(mode).get(degree);
        return midiNote;
    }
}
