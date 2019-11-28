package generative;

import datastructures.Degree;
import datastructures.Key;
import datastructures.Mode;

import java.util.ArrayList;

public class NGapsChordModel extends ChordModel {

    private int numNotes; // -- Number of notes in chords --
    // TODO Need to decide how gaps are specified, either as gaps between
    // TODO each note, or as gaps above the root note
    // TODO Current makeChords() algorithm uses gaps above root note
    private ArrayList<Integer> gaps; // -- Degree gaps in chords --
    private int[][] chords; // -- The chords as lists of integers --
    private final Degree[] degrees = {Degree.I, Degree.II, Degree.III, Degree.IV,
        Degree.V, Degree.VI, Degree.VII};


    /*
        Number of gaps specified must be equal to
        number of notes minus 1
     */
    public NGapsChordModel(Key key, Mode mode, int numNotes, ArrayList<Integer> gaps) {
        super(key, mode);
        this.numNotes = numNotes;
        this.gaps = gaps;
        makeChords();
        makeChain();
    }

    /*
        For each scale degree, make a chord of n notes with
        semitone steps specified by gaps
     */
    private void makeChords() {
        chords = new int[degrees.length][numNotes];
        for (int i = 0; i < chords.length; i++) {
            for (int y = 0; y < numNotes; y++) {
                if (y == 0) {
                    chords[i][y] = notes.get(degrees[i]);
                } else {
                    int index = (i + gaps.get(y - 1)) % degrees.length;
                    chords[i][y] = notes.get(degrees[index]);
                }
            }
        }
    }

    public void printChords() {
        for (int i = 0; i < chords.length; i++) {
            for (int y = 0; y < numNotes; y++) {
                System.out.print(chords[i][y] + " ");
            }
            System.out.println();
        }
    }

    private void makeChain() {

    }

    @Override
    public ArrayList<Integer> nextChord() {
        return null;
    }
}
