/*
    Receives events from the transport,
    and sends event messages to their specified MIDI device and channel
 */

import javax.sound.midi.*;

public class MIDIHandler {

    private MidiDevice device;
    private Receiver receiver;
    private ShortMessage message;

    public void openMIDIDevice() {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        try {
            device = MidiSystem.getMidiDevice(infos[3]);
            device.open();
            receiver = device.getReceiver();
            message = new ShortMessage();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void handle(Event event) {
        /*
            Extract MIDI device, channel and note info from event,
            then send the note to the device on the channel
         */
        int midiNote = event.trigger();
        try {
            message.setMessage(ShortMessage.NOTE_ON, 0, midiNote, 100);
            receiver.send(message, -1);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        try {
            message.setMessage(ShortMessage.NOTE_OFF, 0, midiNote, 0);
            receiver.send(message, -1);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        receiver.close();
        device.close();
    }
}
