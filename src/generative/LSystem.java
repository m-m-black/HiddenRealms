package generative;

import datastructures.Voice;
import events.ChordEvent;
import events.Event;
import events.RhythmEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class LSystem {

    private ArrayList<Integer>[] rules;
    private ArrayList<Integer> system;
    private int noteStart;
    private int density;
    private int wildcardStart; // -- Is also maximum value in L-System --
    private int wildcardEnd;
    private Random random;

    public LSystem(int noteStart, int density) {
        int axiom = 0; // -- Start with an axiom of 0 for now --
        system = new ArrayList<>();
        system.add(axiom);
        this.noteStart = 36 + noteStart;
        this.density = density;
        random = new Random();
    }

    public void setWildcards(int n, int m) {
        wildcardStart = n;
        wildcardEnd = m;
    }

    public void parseRules(String ruleString) {
        String[] tokens = ruleString.split("/");
        rules = new ArrayList[tokens.length];
        for (int i = 0; i < rules.length; i++) {
            rules[i] = new ArrayList<>();
            String[] fragments = tokens[i].split("_");
            for (String f: fragments) {
                rules[i].add(Integer.parseInt(f));
            }
        }
    }

    public String makeRules(int n) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int r = random.nextInt(4) + 1; // Length of fragment
            for (int j = 0; j < r; j++) {
                int e = random.nextInt(n);
                stringBuilder.append(e + "_");
            }
            if (i < n-1) {
                stringBuilder.append("/");
            }
        }
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    public void printRules() {
        for (ArrayList<Integer> a: rules) {
            for (int i: a) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    public void printSystem() {
        System.out.println("Generated");
        for (int i: system) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public void generate() {
        ArrayList<Integer> next = new ArrayList<>();
        for (int i: system) {
            for (int e: rules[i]) {
                next.add(e);
            }
        }
        system = next;
    }

    public ArrayList<Integer> getSystem() {
        return system;
    }

    public Voice getSystemAsVoice() {
        /*
            Convert system from list to datastructures.Voice and return it
         */
        Voice voice = new Voice(system.size());
        for (int i = 0; i < system.size(); i++) {
            Event e = new RhythmEvent(mapElemToNote(system.get(i)));
            voice.addEvent(e, i);
        }
        return voice;
    }

    public Voice getSystemAtCurrentValues() {
        // Build voice at current density and noteStart settings
        Voice voice = getSystemAsVoiceAtDensityKeepNth(density);
        return voice;
    }

    /*
        Return voice containing system at lower density.
        Density value of 2 = keep every second note.
        Density value of 3 = keep every third note.
        Etc.
     */
    // TODO Throw exception if density value is less than 2
    // TODO as 1 is already maximum density.
    public Voice getSystemAsVoiceAtDensityKeepNth(int density) {
        Voice voice = new Voice(system.size());
        HashMap<Integer, Integer> counters = new HashMap<>();
        for (int i = 0; i < system.size(); i++) {
            int elem = system.get(i);
            // Create counter for each element upon first occurrence
            if (!counters.containsKey(elem)) {
                counters.put(elem, 0);
            }
            // Add events when counter is 0, null otherwise
            if (counters.get(elem) == 0) {
                Event e = new RhythmEvent(mapElemToNote(elem));
                e.setMidiChannel(0);
                e.setVelocity(random.nextInt(127));
                voice.addEvent(e, i);
                counters.replace(elem, 1);
            } else {
                voice.addEvent(null, i);
                counters.replace(elem, counters.get(elem) + 1);
            }
            // Reset counter if it equals the density setting
            if (counters.get(elem) == density) {
                counters.replace(elem, 0);
            }
        }
        return voice;
    }

    /*
        Return voice containing system at lower density.
        Density value of 2 = drop every second note.
        Density value of 3 = drop every third note.
        Etc.
     */
    // TODO Throw exception if density value is less than 2
    // TODO as 1 is already maximum density.
    // TODO Also need to figure out how to handle a density value
    // TODO of 1, which should mean full density, but does not
    // TODO result in this in the code below.
    public Voice getSystemAsVoiceAtDensityDropNth(int density) {
        Voice voice = new Voice(system.size());
        HashMap<Integer, Integer> counters = new HashMap<>();
        for (int i = 0; i < system.size(); i++) {
            int elem = system.get(i);
            // Create counter for each element upon first occurrence
            if (!counters.containsKey(elem)) {
                counters.put(elem, 0);
            }
            // Add events when counter is < density, null otherwise
            if (counters.get(elem) < density - 1) {
                Event e = new RhythmEvent(mapElemToNote(elem));
                voice.addEvent(e, i);
                counters.replace(elem, counters.get(elem) + 1);
            } else {
                voice.addEvent(null, i);
                counters.replace(elem, counters.get(elem) + 1);
            }
            // Reset counter if it equals the density setting
            if (counters.get(elem) == density) {
                counters.replace(elem, 0);
            }
        }
        return voice;
    }

    public Voice getSystemAsChordVoice(ChordModel chordModel) {
        int theValue = 0;
        Voice voice = new Voice(system.size());
        ChordEvent e = new ChordEvent(chordModel);
        e.setMidiChannel(1);
        for (int i = 0; i < system.size(); i++) {
            int elem = system.get(i);
            if (elem == theValue) {
                voice.addEvent(e, i);
                theValue++;
            } else {
                voice.addEvent(null, i);
            }
            if (theValue == wildcardStart + 1) {
                theValue = 0;
            }
        }
        return voice;
    }

    // -- Set MIDI note values for each alphabet element --
    private int mapElemToNote(int n) {
        if (n == wildcardStart) {
            return noteStart + (random.nextInt(wildcardEnd - wildcardStart + 1) + wildcardStart);
        } else {
            return noteStart + n;
        }
    }

    public void setNoteStart(int noteStart) {
        this.noteStart = this.noteStart + noteStart;
    }

    public void setDensity(int density) {
        this.density = density;
    }
}
