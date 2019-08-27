public class ControlSystem {

    public void run() {
        System.out.println("Up and running...");
        Sequence seq = new Sequence(16);
        Voice<Event> voice = new Voice<>(seq.getSteps());
        seq.addVoice(voice);
    }
}
