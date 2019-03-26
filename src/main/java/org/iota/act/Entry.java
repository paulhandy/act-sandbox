package org.iota.act;

import java.util.function.Consumer;

public class Entry {
    public int size;
    Consumer<String> fn;
    public void accept(String effect) {
        fn.accept(effect);
    }
}
