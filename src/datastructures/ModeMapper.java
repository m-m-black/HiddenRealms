package datastructures;

import datastructures.Degree;
import datastructures.Mode;

import java.util.HashMap;

public class ModeMapper {

    private static HashMap<Degree, Integer> map;
    private Mode mode;

    public static HashMap<Degree, Integer> getMap(Mode mode) {
        map = null;
        switch (mode) {
            case IONIAN:
                map = getIonian();
                break;
            case DORIAN:
                map = getDorian();
                break;
            case PHRYGIAN:
                map = getPhrygian();
                break;
            case LYDIAN:
                map = getLydian();
                break;
            case MIXOLYDIAN:
                map = getMixolydian();
                break;
            case AEOLIAN:
                map = getAeolian();
                break;
            case LOCRIAN:
                map = getLocrian();
                break;
            default:
                break;
        }
        return map;
    }

    private static HashMap<Degree, Integer> getIonian() {
        map = new HashMap<>();
        map.put(Degree.I, 60);
        map.put(Degree.II, 62);
        map.put(Degree.III, 64);
        map.put(Degree.IV, 65);
        map.put(Degree.V, 67);
        map.put(Degree.VI, 69);
        map.put(Degree.VII, 71);
        return map;
    }

    private static HashMap<Degree, Integer> getDorian() {
        map = new HashMap<>();
        map.put(Degree.I, 60);
        map.put(Degree.II, 62);
        map.put(Degree.III, 63);
        map.put(Degree.IV, 65);
        map.put(Degree.V, 67);
        map.put(Degree.VI, 69);
        map.put(Degree.VII, 70);
        return map;
    }

    private static HashMap<Degree, Integer> getPhrygian() {
        map = new HashMap<>();
        map.put(Degree.I, 60);
        map.put(Degree.II, 61);
        map.put(Degree.III, 63);
        map.put(Degree.IV, 65);
        map.put(Degree.V, 67);
        map.put(Degree.VI, 68);
        map.put(Degree.VII, 70);
        return map;
    }

    private static HashMap<Degree, Integer> getLydian() {
        map = new HashMap<>();
        map.put(Degree.I, 60);
        map.put(Degree.II, 62);
        map.put(Degree.III, 64);
        map.put(Degree.IV, 66);
        map.put(Degree.V, 67);
        map.put(Degree.VI, 69);
        map.put(Degree.VII, 71);
        return map;
    }

    private static HashMap<Degree, Integer> getMixolydian() {
        map = new HashMap<>();
        map.put(Degree.I, 60);
        map.put(Degree.II, 62);
        map.put(Degree.III, 64);
        map.put(Degree.IV, 65);
        map.put(Degree.V, 67);
        map.put(Degree.VI, 69);
        map.put(Degree.VII, 70);
        return map;
    }

    private static HashMap<Degree, Integer> getAeolian() {
        map = new HashMap<>();
        map.put(Degree.I, 60);
        map.put(Degree.II, 62);
        map.put(Degree.III, 63);
        map.put(Degree.IV, 65);
        map.put(Degree.V, 67);
        map.put(Degree.VI, 68);
        map.put(Degree.VII, 70);
        return map;
    }

    private static HashMap<Degree, Integer> getLocrian() {
        map = new HashMap<>();
        map.put(Degree.I, 60);
        map.put(Degree.II, 61);
        map.put(Degree.III, 63);
        map.put(Degree.IV, 65);
        map.put(Degree.V, 66);
        map.put(Degree.VI, 68);
        map.put(Degree.VII, 70);
        return map;
    }
}
