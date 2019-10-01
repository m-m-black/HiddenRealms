package transport;

import utility.Utility;

import javax.sound.midi.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class LSystemTransport {

    int currentStep;
    Integer[] sequence;
    int n; // -- MIDI note value to be output --
    MidiDevice device;
    Receiver receiver;
    ShortMessage message;
    ScheduledExecutorService executorService;
    ScheduledFuture<?> futureTask;
    Runnable task;

    public LSystemTransport(ArrayList<Integer> sequence) {
        currentStep = 0;
        this.sequence = sequence.toArray(new Integer[sequence.size()]);
        n = 0;
        openMIDIDevice();
        message = new ShortMessage();
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    private void openMIDIDevice() {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        try {
            device = MidiSystem.getMidiDevice(infos[3]);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void start(int tempo) {
        if (executorService.isShutdown()) {
            executorService = Executors.newSingleThreadScheduledExecutor();
        }
        currentStep = 0;
        try {
            device.open();
            receiver = device.getReceiver();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        task = new Runnable() {
            @Override
            public void run() {
                outputNote(currentStep);
                nextStep();
            }
        };
        changeReadInterval(tempo);
    }

    public void stop() {
        futureTask.cancel(true);
        executorService.shutdown();
        receiver.close();
        device.close();
    }

    public void changeReadInterval(int tempo) {
        long time = Utility.bpmToMS(tempo);
        if (time > 0) {
            if (futureTask != null) {
                futureTask.cancel(true);
            }
            futureTask = executorService.scheduleAtFixedRate(task, 0, time, TimeUnit.MILLISECONDS);
        }
    }

    private void outputNote(int currentStep) {
        n = setN(sequence[currentStep]);
        try {
            message.setMessage(ShortMessage.NOTE_ON, 0, n, 100);
            receiver.send(message, -1);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        try {
            message.setMessage(ShortMessage.NOTE_OFF, 0, n, 0);
            receiver.send(message, -1);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    private void nextStep() {
        if (currentStep < sequence.length - 1) {
            currentStep++;
        } else {
            currentStep = 0;
        }
    }

    private int setN(int n) {
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
