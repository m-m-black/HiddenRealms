import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ControlSystem {

    /*
        Global transport and handler objects
     */
    MIDIHandler handler = new MIDIHandler(120);
    Transport transport = new Transport(handler);
    Sequence sequence = new Sequence();

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
                    case "CUE":
                        sequence = new Sequence();
                        // LSystem voice
                        Voice lSysVoice = lSystem.getSystemAsVoice();
                        for (Event e: lSysVoice.getRow()) {
                            e.setMidiChannel(0);
                        }
                        sequence.addVoice(lSysVoice);
                        // EuclideanRhythm
                        EuclideanRhythm euclideanRhythm = new EuclideanRhythm();
                        // Snare pattern
                        int[] snarePattern = euclideanRhythm.generate(16, 2, 4);
                        Voice snareVoice = new Voice(snarePattern.length);
                        Event snareEvent = new RhythmEvent(39);
                        snareEvent.setMidiChannel(1);
                        for (int i = 0; i < snarePattern.length; i++) {
                            if (snarePattern[i] == 1) {
                                snareVoice.addEvent(snareEvent, i);
                            }
                        }
                        sequence.addVoice(snareVoice);
                        int[] rhythm = euclideanRhythm.generate(16, 4);
                        // Markov voice
                        Voice markovVoice = new Voice(rhythm.length);
                        Event event = new MarkovEvent(markovMatrix, Mode.DORIAN);
                        event.setMidiChannel(2);
                        for (int i = 0; i < rhythm.length; i++) {
                            if (rhythm[i] == 1) {
                                markovVoice.addEvent(event, i);
                            } else {
                                markovVoice.addEvent(null, i);
                            }
                        }
                        sequence.addVoice(markovVoice);
                        transport.setSequence(sequence);
                        break;
                    case "START":
                        transport.start(120);
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
                    case "EUC":
                        int notes = Integer.parseInt(tokens[1]);
                        int steps = Integer.parseInt(tokens[2]);
                        int rotation = 0;
                        if (tokens.length == 4) {
                            rotation = Integer.parseInt(tokens[3]);
                        }
                        euclideanRhythm = new EuclideanRhythm();
                        rhythm = euclideanRhythm.generate(steps, notes, rotation);
                        for (int i: rhythm) {
                            System.out.print(i + " ");
                        }
                        System.out.println();
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
