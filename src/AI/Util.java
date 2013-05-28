//package AI;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class Util {

    public static final HashMap<String, Byte> pieceMap = new HashMap<String, Byte>();
    public static final HashMap<String, Integer> winMap = new HashMap<String, Integer>();

    public static final byte gamePiece_r = new Byte((byte) 00);
    public static final byte gamePiece_b = new Byte((byte) 01);
    public static final byte gamePiece_g = new Byte((byte) 10);
    public static final byte gamePiece_s = new Byte((byte) 11);
    
    public Util(){
        
        pieceMap.put("r", gamePiece_r);
        pieceMap.put("b", gamePiece_b);
        pieceMap.put("g", gamePiece_g);
        pieceMap.put("s", gamePiece_s);

        winMap.put(Arrays.toString(new Byte[] { gamePiece_b, gamePiece_b, gamePiece_g, gamePiece_g }), 5);
        winMap.put(Arrays.toString(new Byte[] { gamePiece_g, gamePiece_g, gamePiece_b, gamePiece_b }), 5);
        winMap.put(Arrays.toString(new Byte[] { gamePiece_b, gamePiece_g, gamePiece_g, gamePiece_b }), 4);
        winMap.put(Arrays.toString(new Byte[] { gamePiece_g, gamePiece_b, gamePiece_b, gamePiece_g }), 4);
        winMap.put(Arrays.toString(new Byte[] { gamePiece_b, gamePiece_g, gamePiece_b, gamePiece_g }), 3);
        winMap.put(Arrays.toString(new Byte[] { gamePiece_g, gamePiece_b, gamePiece_g, gamePiece_b }), 3);

        winMap.put(Arrays.toString(new Byte[] { gamePiece_r, gamePiece_r, gamePiece_g, gamePiece_g }), -5);
        winMap.put(Arrays.toString(new Byte[] { gamePiece_g, gamePiece_g, gamePiece_r, gamePiece_r }), -5);
        winMap.put(Arrays.toString(new Byte[] { gamePiece_r, gamePiece_g, gamePiece_g, gamePiece_r }), -4);
        winMap.put(Arrays.toString(new Byte[] { gamePiece_g, gamePiece_r, gamePiece_r, gamePiece_g }), -4);
        winMap.put(Arrays.toString(new Byte[] { gamePiece_r, gamePiece_g, gamePiece_r, gamePiece_g }), -3);
        winMap.put(Arrays.toString(new Byte[] { gamePiece_g, gamePiece_r, gamePiece_g, gamePiece_r }), -3);
        
    }
    
    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Entry<T, E> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
