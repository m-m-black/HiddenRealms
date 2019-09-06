/*
    Receives the sequence and steps through it,
    sending events to the MIDI handler at a specified tempo.
 */

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Transport {

    private int currentStep;
    private Sequence sequence;
    private double tempo; // -- measured in BPM --
    private MIDIHandler handler;
    private ScheduledExecutorService executorService;
    private ScheduledFuture<?> futureTask;
    private Runnable task;
    private boolean running;

    public Transport(MIDIHandler handler) {
        this.handler = handler;
        currentStep = 0;
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
            if (executorService.isShutdown()) {
                executorService = Executors.newSingleThreadScheduledExecutor();

            }
            currentStep = 0;
            task = new Runnable() {
                @Override
                public void run() {
                    outputNotes(currentStep);
                    nextStep();
                }
            };
            changeReadInterval(tempo);
        }
    }

    public void stop() {
        /*
            Stop stepping through sequence
         */
        futureTask.cancel(true);
        executorService.shutdown();
    }

    private void send(Event e) {
        handler.handle(e);
    }

    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    private void outputNotes(int currentStep) {
        /*
            Send events to MIDIHandler
         */
        for (Voice v: sequence.getVoices()) {
            send(v.getRow()[currentStep]);
        }
    }

    private void nextStep() {
        if (currentStep < sequence.getSteps() - 1) {
            currentStep++;
        } else {
            currentStep = 0;
        }
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
}
