import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ControlSystem {

    /*
        Global transport and handler objects
     */
    MIDIHandler handler = new MIDIHandler();
    Transport transport = new Transport(handler);
    LSystemTransport lSystemTransport;
    Sequence sequence;

    /*
        Generative algorithm objects
     */
    LSystem lSystem = new LSystem();

    /*
        User input variables
     */
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String line;
    boolean quit = false;
    String[] tokens;
    String command;

    public void run() {
        System.out.println("Welcome to the Hidden Realms...");

        try {
            while (!quit && (line = reader.readLine()) != null) {
                tokens = line.split(" ");
                command = tokens[0];

                switch (command.toUpperCase()) {
                    case "LSYS":
                        lSystem = new LSystem();
                        lSystem.parseRules(tokens[1]);
                        lSystem.printRules();
                        break;
                    case "GEN":
                        lSystem.generate();
                        lSystem.printSystem();
                        break;
                    case "START":
                        transport.setSequence(lSystem.getSystemAsSequence());
                        transport.start(120);
                        break;
                    case "STOP":
                        transport.stop();
                        break;
                    case "QUIT":
                        quit = true;
                        break;
                    default:
                        System.out.println("Unknown command");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
