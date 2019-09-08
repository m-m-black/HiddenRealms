/*
    Stores the score data for the sequence,
    as a list of voices, where each voice contains a row of events.
 */

import java.util.ArrayList;

public class Sequence {

    private ArrayList<Voice> voices; // -- List of rows in the sequence --

    public Sequence() {
        voices = new ArrayList<>();
    }

    public void addVoice(Voice voice) {
        voices.add(voice);
    }

    public ArrayList<Voice> getVoices() {
        return voices;
    }
}
