package org.iota.act;

import java.io.IOException;

import static org.iota.act.Act.HASH_LENGTH;

public class TritBuffer {
    byte[] innerBytes;
    static final int TRIT_INCREMENT = 2;
    static final int TRIT_INDEX_UPPER = 6;
    int arrayIndex = 0, tritIndex = 0, end;

    public static TritBuffer wrap(byte[] bytes) {
        TritBuffer b = new TritBuffer();
        b.innerBytes = bytes;
        b.end = bytes.length;
        return b;
    }

    public TritBuffer put(byte trit) {
        innerBytes[arrayIndex] = (byte) (trit | (innerBytes[arrayIndex] << tritIndex));
        increment(TRIT_INCREMENT, TRIT_INDEX_UPPER);
        return this;
    }

    public byte get() {
        byte out;
        out = (byte) ((innerBytes[arrayIndex] & (3 << tritIndex)) >> tritIndex);
        increment(TRIT_INCREMENT, TRIT_INDEX_UPPER);
        return out;
    }

    public byte[] get(int n) {
        byte[] out = new byte[n / 4 + (n % 4 == 0? 0:1)];
        for(int i = 0, k; i < n; i += 4) {
            k = i / 4;
            for(int j = 0; j+i < n && j < 4; j++) {
                out[k] |= get()<< (j << 1);
            }
        }
        return out;
    }

    public byte getQuad() throws IOException {
        return get(4)[0];
    }

    public int getPositiveInteger() throws IOException {
        int out = 0;
        for(int i = 0, v = get(); v != 3; i++, v = get()) {
            out += (v & 1) << i;
        }
        return out;
    }

    public String getHash() throws IOException {
        return new String(get(HASH_LENGTH));
    }


    void increment(int tritIncrement, int tritIndexUpper) {
        tritIndex += tritIncrement;
        if(tritIndex >= tritIndexUpper) {
            tritIndex = 0;
            arrayIndex++;
        }
    }

    public TritBuffer put(byte[] tritArray) {
        for(byte trit:tritArray) {
            put(trit);
        }
        return this;
    }

    public String get(int start, int length) {
        return null;
    }
}
