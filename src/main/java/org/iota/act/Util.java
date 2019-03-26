package org.iota.act;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.util.Arrays;

public class Util {
    private static short[] SHORT_MAGNITUDES = new short[]{1, 3, 9, 27, 81, 243, 729, 2187, 6561};

    public static Short[] tritStringToShort(String trits) {
        return Arrays.stream(trits.split("(?<=\\G.{9})")).map(trint -> {
            short o = 0, v = 0;
            for(int i = 0; i < trint.length(); i++) {
                switch (trint.charAt(i)) {
                    case 'T': v = -1; break;
                    case '1': v = 1; break;
                    case '0': v = 0; break;
                    default: break;
                }
                o +=  v * SHORT_MAGNITUDES[i];
            }
            return o;
        }).toArray(Short[]::new);
    }

    public static void printShortTrits(short[] trits) {
        byte[] bytes = new byte[trits.length * 2];
        ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(trits);
        System.out.println(bytes);
    }
}
