/*
    Stores the score data for the sequence,
    as a list of voices, where each voice contains a row of events.
 */

import java.util.ArrayList;

public class Sequence {

    private ArrayList<Voice> voices; // -- List of rows in the sequence --
    private int steps; // -- Length of the sequence, in steps --

    public Sequence(int steps) {
        this.steps = steps;
        voices = new ArrayList<>();
    }

    public void addVoice(Voice voice) {
        voices.add(voice);
    }

    public int getSteps() {
        return steps;
    }

    public ArrayList<Voice> getVoices() {
        return voices;
    }
}
