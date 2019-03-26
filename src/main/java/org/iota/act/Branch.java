package org.iota.act;

import java.util.List;
import java.util.Map;

public class Branch implements Knot {
    public Site[] body;
    public Site[] output;
    public Site[] latch;
    int inputLength[];

    public Branch() {}

    public Branch(int[] i) {
        inputLength = i;
    }

    public int inputCount() {
        return inputLength.length;
    }
}
