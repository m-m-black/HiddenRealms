public class ControlSystem {

    public void run() {
        System.out.println("Up and running...");
        Sequence seq = new Sequence(16);
        Row<Event> row = new Row<>(seq.getSteps());
        seq.addRow(row);
    }
}
