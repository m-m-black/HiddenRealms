/*
    Receives events from the transport,
    and sends event messages to their specified MIDI device and channel
 */

import javax.sound.midi.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MIDIHandler {

    private MidiDevice device;
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(16);

    public void openMIDIDevice() {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        try {
            device = MidiSystem.getMidiDevice(infos[3]);
            device.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void handle(Event event) {
        executor.submit(new MIDINoteThread(device, event, 1000));
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
