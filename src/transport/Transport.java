package transport;/*
    Receives the sequence and steps through it,
    sending events to the MIDI handler at a specified tempo.
 */

import datastructures.Sequence;
import datastructures.Voice;
import events.Event;
import transport.MIDIHandler;
import utility.Utility;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Transport {

    private Sequence sequence;
    private double tempo; // -- measured in BPM --
    private MIDIHandler handler;
    private ScheduledExecutorService executorService;
    private ScheduledFuture<?> futureTask;
    private Runnable task;
    private boolean running;

    public Transport(MIDIHandler handler) {
        this.handler = handler;
        tempo = 120;
        executorService = Executors.newSingleThreadScheduledExecutor();
        running = false;
    }

    public void start(int tempo) {
        /*
            Step through sequence and send events to handler
         */
        if (!running) {
            running = true;
            handler.openMIDIDevice();
            if (executorService.isShutdown()) {
                executorService = Executors.newSingleThreadScheduledExecutor();
            }
            for (Voice v: sequence.getVoices()) {
                // Set each voice's current step to 0
                v.reset();
            }
            task = new Runnable() {
                @Override
                public void run() {
                    outputNotes();
                }
            };
            changeReadInterval(tempo);
        }
    }

    public void stop() {
        /*
            Stop stepping through sequence
         */
        if (running) {
            running = false;
            futureTask.cancel(true);
            executorService.shutdown();
            handler.close();
        }
    }

    private void send(Event e) {
        handler.handle(e);
    }

    public void setTempo(double tempo) {
        this.tempo = tempo;
        if (running) {
            changeReadInterval(tempo);
        }
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    private void outputNotes() {
        /*
            Send events to transport.MIDIHandler
         */
        for (Voice v: sequence.getVoices()) {
            // Send event from each voice's current step
            Event e = v.getCurrentEvent();
            if (e != null) {
                send(e);
            }
        }
    }

    public void changeReadInterval(double tempo) {
        long time = Utility.bpmToMS(tempo);
        if (time > 0) {
            if (futureTask != null) {
                futureTask.cancel(true);
            }
            futureTask = executorService.scheduleAtFixedRate(task, 0, time, TimeUnit.MILLISECONDS);
        }
    }
}
