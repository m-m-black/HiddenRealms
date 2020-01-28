package transport;

import events.Event;

import javax.sound.midi.*;

public class MIDINoteThread extends Thread {

    MidiDevice device;
    Event event;
    int sleepTime;

    public MIDINoteThread(MidiDevice device, Event event, int sleepTime) {
        this.device = device;
        this.event = event;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        /*
            Extract MIDI device, channel and note info from event,
            then send the note to the device on the channel
         */
        int midiNote = event.trigger();
        int midiChannel = event.getMidiChannel();
        int velocity = event.getVelocity();
        ShortMessage message = new ShortMessage();
        Receiver receiver = null;
        try {
            receiver = device.getReceiver();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        try { // -- Send a NOTE_ON message --
            message.setMessage(ShortMessage.NOTE_ON, midiChannel, midiNote, velocity);
            receiver.send(message, -1);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        try { // -- Wait for a specified amount of time --
            sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try { // -- Send a NOTE_OFF message --
            message.setMessage(ShortMessage.NOTE_OFF, midiChannel, midiNote, 0);
            receiver.send(message, -1);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        receiver.close();
    }
}
