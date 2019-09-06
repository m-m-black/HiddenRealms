import java.util.ArrayList;

public class LSystem {

    String[] rules;
    int axiom;
    ArrayList<Integer> system;

    public LSystem() {
        axiom = 0; // -- Start with an axiom of 0 for now --
        system = new ArrayList<>();
        system.add(axiom);
    }

    public void parseRules(String ruleString) {
        rules = ruleString.split("/");
    }

    public void printRules() {
        for (String s: rules) {
            System.out.println(s);
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
        for (int i = 0; i < system.size(); i++) {
            int elem = system.get(i);
            String rule = rules[elem];
            for (int y = 0; y < rule.length(); y++) {
                next.add(Integer.parseInt(rule.substring(y, y+1)));
            }
        }
        system = next;
    }
}
