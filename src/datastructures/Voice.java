package datastructures;/*
    A single row within the sequence,
    initialised with the parent events.Event type.
 */

import events.Event;

public class Voice {

    private Event[] row; // Each voice can have a different length
    private int currentStep; // Each voice keeps track of its own current step

    public Voice(int steps) {
        row = new Event[steps];
        currentStep = 0;
        initRow();
    }

    /*
        Events, e, are added to the row externally by
        the generative algorithm used, at index i.
     */
    public void addEvent(Event e, int i) {
        row[i] = e;
    }

    /*
        When creating a new voice, we make an empty row
        for that voice. Events will be added to the row
        by the generative algorithm used.
     */
    private void initRow() {
        for (int i = 0; i < row.length; i++) {
            row[i] = null; // -- init all row elements to null --
        }
    }

    public Event getCurrentEvent() {
        Event event = row[currentStep];
        nextStep();
        return event;
    }

    private void nextStep() {
        if (currentStep < row.length - 1) {
            currentStep++;
        } else {
            currentStep = 0;
        }
    }

    public void reset() {
        currentStep = 0;
    }

    public Event[] getRow() {
        return row;
    }

    public int getSize() {
        return row.length;
    }
}
