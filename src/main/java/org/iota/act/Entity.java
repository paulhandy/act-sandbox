package org.iota.act;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class Entity {
    public Knot branch;

    EntityInput input, output;
    EntityInput[] inputs, outputs;
    AtomicReference<Function<TritBuffer, TritBuffer>>[] affect;

    public void add(Environment environment,
                    int firstOutput,
                    int lastOutput,
                    int delay) {
        List<Entry> entryList = new LinkedList<>();
        for(int i = lastOutput; i-- > firstOutput;) {
            entryList.addAll(Arrays.asList(outputs[i].entries));
            int finalI = i;
        }
        /*
        affect[i].getAndUpdate(f ->
                f.andThen(effect ->
                        environment.enqueue(effect, outputs[finalI], delay)
                )
        );
        */
    }
}
