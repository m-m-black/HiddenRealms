package transport;/*
    Receives events from the transport,
    and sends event messages to their specified MIDI device and channel
 */

import events.ChordEvent;
import events.Event;
import utility.Utility;

import javax.sound.midi.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MIDIHandler {

    private MidiDevice device;
    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(32);
    private int sleepTime;

    public MIDIHandler(int tempo) {
        long ms = Utility.bpmToMS(tempo);
        sleepTime = (int) ms;
    }

    public void openMIDIDevice() {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        try {
            device = MidiSystem.getMidiDevice(infos[3]);
            device.open();
            if (executor.isShutdown()) {
                executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(32);
            }
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void handle(Event event) {
        if (event instanceof ChordEvent) {
            // Split into individual note events
            int[] notes = ((ChordEvent) event).triggerChord();
            for (int n: notes) {
                Event e = new Event(n);
                e.setMidiChannel(event.getMidiChannel());
                executor.submit(new MIDINoteThread(device, e, sleepTime));
            }
        } else {
            executor.submit(new MIDINoteThread(device, event, sleepTime));
        }
    }

    public void close() {
        try {
            executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        device.close();
    }
}
