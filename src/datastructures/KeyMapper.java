package datastructures;

import java.util.HashMap;
import java.util.Map;

public class KeyMapper {

    private static HashMap<Degree, Integer> map;

    public static HashMap<Degree, Integer> getMap(Key key, HashMap<Degree, Integer> modeMap) {
        map = null;
        switch (key) {
            case C:
                map = offsetMap(0, modeMap);
                break;
            case Db:
                map = offsetMap(1, modeMap);
                break;
            case D:
                map = offsetMap(2, modeMap);
                break;
            case Eb:
                map = offsetMap(3, modeMap);
                break;
            case E:
                map = offsetMap(4, modeMap);
                break;
            case F:
                map = offsetMap(5, modeMap);
                break;
            case Gb:
                map = offsetMap(6, modeMap);
                break;
            case G:
                map = offsetMap(7, modeMap);
                break;
            case Ab:
                map = offsetMap(8, modeMap);
                break;
            case A:
                map = offsetMap(9, modeMap);
                break;
            case Bb:
                map = offsetMap(10, modeMap);
                break;
            case B:
                map = offsetMap(11, modeMap);
                break;
            default:
                break;
        }
        return map;
    }

    // Apply offset to mode map in terms of key
    private static HashMap<Degree, Integer> offsetMap(int n, HashMap<Degree, Integer> modeMap) {
        for (Map.Entry<Degree, Integer> e: modeMap.entrySet()) {
            modeMap.replace(e.getKey(), e.getValue() + n);
        }
        return modeMap;
    }
}
