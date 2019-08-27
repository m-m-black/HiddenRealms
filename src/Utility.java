public class Utility {

    public static long bpmToMS(double bpm) {
        double ms = 60000 / bpm;
        return (long) (ms / 4.0);
    }
}
