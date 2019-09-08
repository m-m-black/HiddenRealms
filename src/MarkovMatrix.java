import java.util.*;

public class MarkovMatrix {

    private HashMap<Degree, List<Tuple>> matrix;
    private Degree currentNote;
    Random random = new Random();

    /*
        Randomly generate transition probability values and builds matrix
     */
    public void initMatrix() {
        matrix = new HashMap<>();

        // List of degrees will be used as keys in the matrix
        List<Degree> degrees = new ArrayList<>();
        degrees.add(Degree.I);
        degrees.add(Degree.II);
        degrees.add(Degree.III);
        degrees.add(Degree.IV);
        degrees.add(Degree.V);
        degrees.add(Degree.VI);
        degrees.add(Degree.VII);

        for (Degree d: degrees) {
            List<Tuple> list = new ArrayList<>();
            double sum = 0.0;

            // Generate n random numbers (say n = 7)
            double[] nums = Utility.nSumTo1(7);
            // Add each number > 0 to PQ
            PriorityQueue<Double> numQueue = new PriorityQueue<Double>();
            for (Double n: nums) {
                if (n > 0) {
                    numQueue.add(n);
                }
            }
            // Generate random weight for each degree and add to another PQ
            Comparator<Tuple> comparator = new WeightComparator();
            PriorityQueue<Tuple> degreeQueue = new PriorityQueue<>(comparator);
            for (Degree d1: degrees) {
                Tuple t = new Tuple(random.nextDouble(), d1);
                degreeQueue.add(t);

            }
            // While first PQ still has elements, pair numbers with degrees by creating tuples
            while (!numQueue.isEmpty()) {
                // Add tuples to list for each degree
                double prob = numQueue.poll();
                // Probability value is value + sum of previous values in the list
                Tuple t = new Tuple((prob + sum), degreeQueue.poll().getDegree());
                list.add(t);
                sum += prob;
            }
            matrix.put(d, list);
        }
    }

    public void printMatrix() {
        for (Map.Entry<Degree, List<Tuple>> entry: matrix.entrySet()) {
            System.out.println(entry.getKey());
            for (Tuple t: entry.getValue()) {
                System.out.print(t.getDegree() + ": " + t.getP() + " ");
            }
            System.out.println();
        }
    }

    public Degree getNextNote() {
        if (currentNote == null) {
            currentNote = Degree.I;
        }
        double r = random.nextDouble();
        List<Tuple> list = matrix.get(currentNote);
        for (Tuple t: list) {
            if (r <= t.getP()) {
                currentNote = t.getDegree();
                break;
            }
        }
        return currentNote;
    }
}
