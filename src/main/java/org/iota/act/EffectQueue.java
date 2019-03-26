package org.iota.act;

import java.util.LinkedList;
import java.util.Queue;

public class EffectQueue {
    public EffectQueue next = null;
    public Queue<EffectInterface> effects = new LinkedList<>();

    public EffectQueue get(int n) {
        if(n == 0) {
            return this;
        }
        if (next == null) {
            next = new EffectQueue();
        }
        return next.get(n-1);
    }

    public EffectInterface poll() {
        return effects.poll();
    }

    public void add(TritBuffer effect, EntityInput io) {
        effects.add(new EffectInterface(effect, io));
    }
    static class EffectInterface {
        TritBuffer effect;
        EntityInput io;
        public EffectInterface(TritBuffer e, EntityInput i) {
            effect = e;
            io = i;
        }
    }
}
