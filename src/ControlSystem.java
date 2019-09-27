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
        Individual voice objects
        Voice 0 = rhythm
        Voice 1 = bass
        Voice 2 = chords
        Voice 3 = melody
     */
    Voice rhythmVoice;
    Voice bassVoice;
    Voice chordVoice;
    Voice melodyVoice;

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
                        //lSystem.parseRules(tokens[1]);
                        int n = Integer.parseInt(tokens[1]);
                        lSystem.parseRules(lSystem.makeRules(n));
                        lSystem.printRules();
                        break;
                    case "GEN":
                        lSystem.generate();
                        lSystem.printSystem();
                        rhythmVoice = lSystem.getSystemAsVoice();
                        for (Event e: rhythmVoice.getRow()) {
                            e.setMidiChannel(0);
                        }
                        break;
                    case "CUE":
                        sequence = new Sequence();
                        // LSystem voice
                        sequence.addVoice(rhythmVoice);
                        // EuclideanRhythm
                        EuclideanRhythm euclideanRhythm = new EuclideanRhythm();
                        int[] melodyRhythm = euclideanRhythm.generate(16, 4);
                        // Markov melody voice
                        melodyVoice = new Voice(melodyRhythm.length);
                        Event event = new MarkovEvent(markovMatrix, Mode.DORIAN);
                        event.setMidiChannel(1);
                        for (int i = 0; i < melodyRhythm.length; i++) {
                            if (melodyRhythm[i] == 1) {
                                melodyVoice.addEvent(event, i);
                            } else {
                                melodyVoice.addEvent(null, i);
                            }
                        }
                        // Markov bass voice
                        int[] bassRhythm = euclideanRhythm.generate(16, 3, 2);
                        bassVoice = new Voice(bassRhythm.length);
                        Event event1 = new MarkovEvent(markovMatrix, Mode.DORIAN);
                        event1.setMidiChannel(2);
                        for (int i = 0; i < bassRhythm.length; i++) {
                            if (bassRhythm[i] == 1) {
                                bassVoice.addEvent(event1, i);
                            } else {
                                bassVoice.addEvent(null, i);
                            }
                        }
                        sequence.addVoice(bassVoice);
                        sequence.addVoice(melodyVoice);
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
//                        euclideanRhythm = new EuclideanRhythm();
//                        rhythm = euclideanRhythm.generate(steps, notes, rotation);
//                        for (int i: rhythm) {
//                            System.out.print(i + " ");
//                        }
//                        System.out.println();
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
