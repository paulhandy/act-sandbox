package org.iota.act;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class Environment {
    AtomicReference<EffectQueue> effectQueueRef;
    List<EntityInput> io;

    public void add(Entity entity, int firstInput, int lastInput, int limit) {
        List<Entry> entries = new LinkedList<>();
        for(int i = firstInput; i <= lastInput; i++) {
            entries.addAll(Arrays.asList(entity.inputs[i].entries));
        }
        io.add(new EntityInput(entries.toArray(new Entry[]{}), limit));
    }

    public void enqueue(TritBuffer effect, int delay) {
        effectQueueRef.getAndUpdate(effectQueue -> {
            for(EntityInput i: io) {
                effectQueue.get(delay).add(effect, i);
            }
            return effectQueue;
        });
    }

    public void emit(Effect effect, Consumer<Effect> andThen) {
        for(EntityInput i: io) {
            i.affect(effect, andThen);
        }
    }
}
