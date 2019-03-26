package org.iota.act;

public class TryteBuffer extends TritBuffer {
    public static final String TRYTES = "9ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final byte[][] TRITS_BY_TRYTE = {
            {0, 0, 0}, {1, 0, 0}, {-1, 1, 0}, //9AB
            {0, 1, 0}, {1, 1, 0}, {-1, -1, 1}, //CDE
            {0, -1, 1}, {1, -1, 1}, {-1, 0, 1}, //FGH
            {0, 0, 1}, {1, 0, 1}, {-1, 1, 1}, //IJK
            {0, 1, 1}, {1, 1, 1}, {-1, -1, -1}, //LMN
            {0, -1, -1}, {1, -1, -1}, {-1, 0, -1}, //OPQ
            {0, 0, -1}, {1, 0, -1}, {-1, 1, -1}, //RST
            {0, 1, -1}, {1, 1, -1}, {-1, -1, 0}, //UVW
            {0, -1, 0}, {1, -1, 0}, {-1, 0, 0} //XYZ
    };

    StringBuilder trytes;

    private static byte[] toTrits(char tryte) {
        return TRITS_BY_TRYTE[TRYTES.indexOf(tryte)];
    }

    public static byte[] toTrits(String trytes) {
        TritBuffer tritBuffer;
        byte[] trits;
        int length = trytes.length() * 3 / 4 + (trytes.length() * 3 % 4 != 0? 1:0);
        trits = new byte[length];
        tritBuffer = TritBuffer.wrap(trits);
        for (int i = 0; i < trytes.length(); i++) {
            byte[] tritTriplet = toTrits(trytes.charAt(i));
            tritBuffer.put(tritTriplet);
        }
        return trits;
    }

    private static char tryteFromTrits(byte[] trits, int offset) {
        int index = trits[offset + 0] + 3 * trits[offset + 1] + 9 * trits[offset + 2];
        return TRYTES.charAt((index + 27) % 27);
    }


    public static TritBuffer wrap(String trytes) {
        TryteBuffer t = new TryteBuffer();
        t.trytes = new StringBuilder(trytes);
        return t;
    }

    @Override
    public TritBuffer put(byte trit) {
        byte[] trits = toTrits(trytes.charAt(arrayIndex));
        trits[tritIndex] = trit;
        trytes.setCharAt(arrayIndex, tryteFromTrits(trits, 0));
        increment(1, 2);
        return this;
    }

    @Override
    public byte get() {
        byte t;
        if(arrayIndex >= trytes.length()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        t = toTrits(trytes.charAt(arrayIndex))[tritIndex];
        increment(1, 3);
        switch(t) {
            case 1: return 1;
            case -1: return 2;
            case 0: return 3;
        }
        return 0;
    }

}
