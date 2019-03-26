package org.iota.act;

public class Effect {
    public TritBuffer value;
    public Environment[] environments;
    public int[] delays;

    public Effect(TritBuffer buffer, Environment[] envs, int[] myDelays) {
        value = buffer;
        this.environments = envs;
        delays = myDelays;
    }
}
