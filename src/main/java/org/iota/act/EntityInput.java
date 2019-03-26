package org.iota.act;

import java.util.function.Consumer;

public class EntityInput {
    Entity entity;
    Entry[] entries;
    int limit;

    public EntityInput(Entry[] myEntries, int myLimit) {
        entries = myEntries;
        limit = myLimit;
    }

    void affect(String effect, int start, int end) {

    }

    public void affect(Effect effect, Consumer<Effect> andThen) {
        int i = 0;
        for(Entry entry: entries) {
            entry.accept(effect.value.get(i, entry.size));
            i += entry.size;
        }
    }
}
