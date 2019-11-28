package main;

import datastructures.*;
import events.Event;
import events.MarkovEvent;
import generative.EuclideanRhythm;
import generative.LSystem;
import generative.MarkovMatrix;
import generative.NGapsChordModel;
import transport.MIDIHandler;
import transport.Transport;
import utility.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import processing.core.PApplet;

public class ControlSystem {

    /*
        Global transport and handler objects
     */
    MIDIHandler handler = new MIDIHandler(120);
    Transport transport = new Transport(handler);
    Sequence sequence = new Sequence();
    PApplet pApplet = new PApplet();

    /*
        Generative algorithm objects
     */
    int lSysDensity = 1;
    int lSysNoteStartOffset = 0;
    LSystem lSystem = new LSystem(lSysNoteStartOffset, lSysDensity);
    MarkovMatrix markovMatrix = new MarkovMatrix();
    int noiseOffset = 0;
    Voice rhythmVoice;
    Voice chordVoice;

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
                    case "TEMPO":
                        transport.setTempo(Integer.parseInt(tokens[1]));
                        break;
                    case "CHORD":
                        ArrayList<Integer> gaps = new ArrayList<>();
                        gaps.add(1);
                        gaps.add(3);
                        NGapsChordModel nGapsChordModel = new NGapsChordModel(Key.C, Mode.DORIAN, 3, gaps);
                        nGapsChordModel.printChords();
                        break;
                    case "NOISE":
                        // Set density to quantised noise value
                        double noise = pApplet.noise(noiseOffset);
                        noiseOffset++;
                        System.out.println(noise);
                        int quantisedN = Utility.quantise(noise);
                        lSysDensity = quantisedN;
                        lSystem.setDensity(quantisedN);
                        // Set note start offset to quantised noise value
                        set(Utility.quantNoteStart(noise));
                        buildRhythmVoice();
                        break;
                    case "LSYS":
                        lSystem = new LSystem(lSysNoteStartOffset, lSysDensity);
                        int n = Integer.parseInt(tokens[1]);
                        int m = Integer.parseInt(tokens[2]);
                        lSystem.parseRules(lSystem.makeRules(n));
                        lSystem.setWildcards(n - 1, m - 1);
                        lSystem.printRules();
                        break;
                    case "DENS": // -- Changes the density of the rhythm --
                        dens(Integer.parseInt(tokens[1]));
                        buildRhythmVoice();
                        sequence.replace(rhythmVoice, 0);
                        break;
                    case "GEN":
                        lSystem.generate();
                        lSystem.printSystem();
                        buildRhythmVoice();
                        break;
                    case "SET": // -- Changes the range of MIDI note values --
                        set(Integer.parseInt(tokens[1]));
                        buildRhythmVoice();
                        sequence.replace(rhythmVoice, 0);
                        break;
                    case "CUE":
                        cue();
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
                        EuclideanRhythm euclideanRhythm = new generative.EuclideanRhythm();
                        int[] rhythm = euclideanRhythm.generate(steps, notes, rotation);
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

    // Build sequence and add it to the transport
    private void cue() {
        buildChordVoice();
        sequence = new Sequence();
        sequence.addVoice(rhythmVoice);
        sequence.addVoice(chordVoice);
        transport.setSequence(sequence);
    }

    // Set global and local density values
    private void dens(int n) {
        lSysDensity = n; // -- Global density value --
        lSystem.setDensity(lSysDensity); // -- Local density value --
    }

    // Build rhythm voice based on current lSystem values
    private void buildRhythmVoice() {
        rhythmVoice = lSystem.getSystemAtCurrentValues();
    }

    // Build chord voice based on current lSystem
    private void buildChordVoice() {
        chordVoice = lSystem.getSystemAsChordVoice();
    }

    // Set global and local note start values
    private void set(int n) {
        // Update global note start value
        lSysNoteStartOffset += n;
        // Update local note start value
        lSystem.setNoteStart(n);
    }
}
