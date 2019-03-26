package org.iota.act;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Act act = null;
        try {
            act = new Act(args);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        act.start();
    }
}
