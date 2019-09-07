import java.util.Random;

public class Utility {

    public static long bpmToMS(double bpm) {
        double ms = 60000 / bpm;
        return (long) (ms / 4.0);
    }

    /*
        Generate a list of n random doubles that sum to 1.0
     */
    public static double[] nSumTo1(int n) {
        Random r = new Random();
        double[] nums = new double[n];
        int sum = 100;

        for (int i = 0; i < nums.length-1; i++) {
            int randInt = r.nextInt(200);
            int num = randInt % (sum + 1);
            sum -= num;
            nums[i] = num / 100.0;
        }
        nums[nums.length-1] = sum / 100.0;

        return nums;
    }
}
