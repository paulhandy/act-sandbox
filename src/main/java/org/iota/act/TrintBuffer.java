package org.iota.act;

import java.io.IOException;

public class TrintBuffer extends TritBuffer {
    static final int TRITS_PER_SHORT = 9;
    StringBuilder trints;

    public static short[] short_radix = new short[] {1,3,9,27,81,243,729,2187,6561};
    public static TritBuffer wrap(String trints) {
        TrintBuffer t = new TrintBuffer();
        t.trints = new StringBuilder(trints);
        t.end = trints.length();
        return t;
    }

    @Override
    public byte get() {
        byte out;
        out = (byte) (3 & (shortBct(trints.charAt(arrayIndex)) >> tritIndex));
        increment(2, 7);
        return out;
    }

    private int shortBct(int v) {
        int buf = 0;
        for(int i = TRITS_PER_SHORT; i-- > 0;) {
            buf <<=2;
            buf |= 3;
            if (v > short_radix[i] / 2) {
                v -= short_radix[i];
                buf ^= 2;
            } else if (v < -(short_radix[i] / 2)) {
                v += short_radix[i];
                buf ^= 1;
            }
        }
        return buf;
    }

    private short bctShort(int j) throws IOException {
        short v = 0;
        for(int i = 0; i < j; i++) {
            switch(get()) {
                case 1: v += short_radix[i]; break;
                case 2: v -= short_radix[i]; break;
            }
        }
        return v;
    }

}
