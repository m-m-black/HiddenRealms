package datastructures;

import java.util.HashMap;

public class KeyMapper {

    private static HashMap<Degree, Integer> map;

    // TODO Need to get mode first, then apply offset with key
    public static HashMap<Degree, Integer> getMap(Key key, Mode mode) {
        map = null;
        switch (key) {
            case C:
                map = getC();
                break;
            default:
                break;
        }
        return map;
    }

    private static HashMap<Degree, Integer> getC() {
        map = new HashMap<>();
        return map;
    }
}
