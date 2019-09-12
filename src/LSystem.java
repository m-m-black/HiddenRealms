import java.util.ArrayList;

public class LSystem {

    private ArrayList<Integer>[] rules;
    private ArrayList<Integer> system;

    public LSystem() {
        int axiom = 0; // -- Start with an axiom of 0 for now --
        system = new ArrayList<>();
        system.add(axiom);
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
            Convert system from list to Voice and return it
         */
        Voice voice = new Voice(system.size());
        for (int i = 0; i < system.size(); i++) {
            Event e = new RhythmEvent(mapElemToNote(system.get(i)));
            voice.addEvent(e, i);
        }
        return voice;
    }

    private int mapElemToNote(int n) {
        int returnValue = 0;
        if (n == 0) {
            returnValue = 36;
        } else if (n == 1) {
            returnValue = 37;
        } else if (n == 2) {
            returnValue = 38;
        } else if (n == 3) {
            returnValue = 39;
        } else if (n == 4) {
            returnValue = 40;
        } else if (n == 5) {
            returnValue = 41;
        } else if (n == 6) {
            returnValue = 42;
        } else if (n == 7) {
            returnValue = 43;
        }
        return returnValue;
    }
}
