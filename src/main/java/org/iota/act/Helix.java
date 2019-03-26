package org.iota.act;

public class Helix {
    public static byte helix(byte input) {
        switch (input) {
            case 0b111111: // 000
            case 0b010101: // 111
            case 0b101010: // ---
            case 0b110110: // 1-0
            case 0b111001:
            case 0b101101:
            case 0b011110:
            case 0b011011:
            case 0b100111:
                return input;
            case 0b011111: // 100
            case 0b110111:
            case 0b111101:
            case 0b010110: // 11-
            case 0b100101:
            case 0b011001:
            case 0b101011: // --0
            case 0b001110:
            case 0b111010:
                return (byte) (input >> 2 | ((input & 3) << 4));
            case 0b101001: // --1
            case 0b100110:
            case 0b011010:
            case 0b010111: // 110
            case 0b011101:
            case 0b110101:
            case 0b101111: // -00
            case 0b111110:
            case 0b111011:
                return (byte) (input >> 4 | ((input & 0b1111) << 2));
            default:
                return 0;
        }
    }
}
