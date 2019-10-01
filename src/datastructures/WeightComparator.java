package datastructures;

import datastructures.Tuple;

import java.util.Comparator;

public class WeightComparator implements Comparator<Tuple> {

    @Override
    public int compare(Tuple x, Tuple y) {
        if (x.getP() < y.getP()) {
            return -1;
        }
        if (x.getP() > y.getP()) {
            return 1;
        }
        return 0;
    }
}
