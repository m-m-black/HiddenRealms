import java.util.ArrayList;

public class LSystem {

    ArrayList<Integer>[] rules;
    int axiom;
    ArrayList<Integer> system;

    public LSystem() {
        axiom = 0; // -- Start with an axiom of 0 for now --
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
}
