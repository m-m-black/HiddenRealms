public class EuclideanRhythm {

    public int[] generate(int steps, int notes) {
        return makeRhythm(steps, notes, 0);
    }

    public int[] generate(int steps, int notes, int rotation) {
        return makeRhythm(steps, notes, rotation);
    }

    private int[] makeRhythm(int steps, int notes, int rotation) {
        int[] rhythm = new int[steps];
        int bucket = 0;
        for (int i = 0; i < steps; i++) {
            bucket += notes;
            if (bucket >= steps) {
                bucket -= steps;
                rhythm[i] = 1;
            } else {
                rhythm[i] = 0;
            }
        }
        return rotate(rhythm, rotation + 1);
    }

    private int[] rotate(int[] rhythm, int rotation) {
        int[] rotatedRhythm = new int[rhythm.length];
        int val = rhythm.length - rotation;
        for (int i = 0; i < rhythm.length; i++) {
            rotatedRhythm[i] = rhythm[Math.abs(i + val) % rhythm.length];
        }
        return rotatedRhythm;
    }
}
