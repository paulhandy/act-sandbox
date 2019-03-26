package org.iota.act;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Lut implements Knot {

    public static byte rowToTritQuad(byte row) {
        byte quad = 0;
        quad += (byte) ((row / 27) % 3) * 27;
        quad += (byte) ((row / 9) % 3) * 9;
        quad += (byte) ((row / 3) % 3) * 3;
        quad += (byte) (row % 3);
        return (byte) (quad - 40);
    }

    public static byte tritQuadToRow(byte quad) {
        quad += 40;
        byte r = (byte) (quad % 4);
        r |= ((quad / 4) % 4) << 2;
        r |= ((quad / 16) % 4) << 4;
        return (byte) (r & 63);
    }

    public void addRow(byte readQuad) {
        throw new NotImplementedException();
    }
}
