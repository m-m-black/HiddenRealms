import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ControlSystem {

    /*
        Global transport and handler objects
     */
    MIDIHandler handler = new MIDIHandler();
    Transport transport = new Transport(handler);
    Sequence sequence;

    /*
        Generative algorithm objects
     */
    LSystem lSystem = new LSystem();
    MarkovMatrix markovMatrix = new MarkovMatrix();

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
//                        transport.setSequence(lSystem.getSystemAsSequence());
//                        transport.start(120);
                        Voice voice = new Voice(1);
                        Event event = new MarkovEvent(markovMatrix, Mode.IONIAN);
                        voice.addEvent(event, 0);
                        sequence = new Sequence();
                        sequence.addVoice(voice);
                        transport.setSequence(sequence);
                        transport.start(30);
                        break;
                    case "STOP":
                        transport.stop();
                        break;
                    case "NUMS":
                        Utility.nSumTo1(7);
                        break;
                    case "MKOV":
                        markovMatrix.initMatrix();
                        markovMatrix.printMatrix();
                        break;
                    case "NEXT":
                        System.out.println(markovMatrix.getNextNote());
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
