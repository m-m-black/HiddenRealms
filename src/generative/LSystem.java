package generative;

import datastructures.Voice;
import events.Event;
import events.RhythmEvent;

import java.util.ArrayList;
import java.util.Random;

public class LSystem {

    private ArrayList<Integer>[] rules;
    private ArrayList<Integer> system;
    private int noteStart;

    public LSystem() {
        int axiom = 0; // -- Start with an axiom of 0 for now --
        system = new ArrayList<>();
        system.add(axiom);
        noteStart = 36;
    }

    public void parseRules(String ruleString) {
        String[] tokens = ruleString.split("/");
        rules = new ArrayList[tokens.length];
        for (int i = 0; i < rules.length; i++) {
            rules[i] = new ArrayList<>();
            for (int y = 0; y < tokens[i].length(); y++) {
                rules[i].add(Integer.parseInt(tokens[i].substring(y, y+1)));
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
                stringBuilder.append(e);
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

    // -- Set MIDI note values for each alphabet element --
    private int mapElemToNote(int n) {
        int returnValue = 0;
        if (n == 0) {
            returnValue = noteStart;
        } else if (n == 1) {
            returnValue = noteStart + 1;
        } else if (n == 2) {
            returnValue = noteStart + 2;
        } else if (n == 3) {
            returnValue = noteStart + 3;
        } else if (n == 4) {
            returnValue = noteStart + 4;
        } else if (n == 5) {
            returnValue = noteStart + 5;
        } else if (n == 6) {
            returnValue = noteStart + 6;
        } else if (n == 7) {
            returnValue = noteStart + 7;
        }
        return returnValue;
    }

    public void setNoteStart(int noteStart) {
        this.noteStart = this.noteStart + noteStart;
    }
}