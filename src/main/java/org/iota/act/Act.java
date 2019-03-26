package org.iota.act;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

/**
 * Abra-Controlled agenT
 */
public class Act {
    static final String nv0 = "";
    static final String split = "[|]"
            , spquant = ";"
            ;
    static final int HASH_LENGTH = 243;
    static final int TRITS_PER_CHAR = 3;
    static final int ENV_LENGTH = HASH_LENGTH / TRITS_PER_CHAR;

    Queue<Queue<Map<String, List<String>>>> effects = new LinkedList<>();
    Environment env0 = new Environment();
    Map<String, Environment> environments = new TreeMap<>();
    Map<String, List<Entry>> entryMap = new TreeMap<>();
    Map<String, Knot[]> qubics = new TreeMap<>();

    public void start() {
        Queue<Map<String, List<String>>> next, remaining;
        Map<String, List<String>> env0 = new TreeMap<>();
        env0.putIfAbsent(nv0, Arrays.asList(new String[]{""}));

        while(true) {
            next = new LinkedList<>();
            next.add(env0);
            if(!effects.isEmpty()) {
                next.addAll(effects.poll());
            }
            while(next.size() != 0) {
                remaining = next;
                next = new LinkedList<>();
                for(Map<String, List<String>> subwave: remaining) {
                    for(Map.Entry<String, List<String>> env: subwave.entrySet()) {
                        for(String effect: env.getValue()) {
                            for(Entry entry: entryMap.getOrDefault(env.getKey(), Arrays.asList(new Entry[0]))) {
                                entry.accept(effect);
                            }
                        }
                    }
                }
            }
        }
    }

    public void wave(Environment initialEnvironment) {
        Queue<Effect> residualEffects = new LinkedList<>();
        residualEffects.add(new Effect(initialEnvironment.effectQueueRef.get().poll().effect, new Environment[]{initialEnvironment}, new int[]{0}));

        for(Effect e;!residualEffects.isEmpty();) {
            e = residualEffects.poll();
            for(Environment env : e.environments) {
                env.emit( e, residualEffects::add );
            }
            residualEffects.stream().filter(effect -> {
                for(int i = 0;i < effect.delays.length; i++) {
                    if(effect.delays[i] == 0) {
                        return true;
                    }
                }
                return false;
            });
        }
    }

    public void quant() {
        // Process env0 first

        // Then run unprocessed effects

        // Then run scheduled effects
    }

    private Effectivity affect(Effect effect) {
        /*
        for(Entry entry: effect.entries) {
            if(entry.accept(effect.value)) {
                effect.entries.remove(entry);

            }
        }
        return Effectivity.EFFECT_COMPLETE;
        */
        throw new NotImplementedException();
    }

    public Act(String[] args) throws Exception {
        byte[] zerohash = new byte[243/4+1];
        Arrays.fill(zerohash, (byte) 0xff);
        environments.put(TritBuffer.wrap(zerohash).getHash(), env0);
        if(args.length != 0) {
            for(String qubic: args[0].split(split)) {
                this.parse(TryteBuffer.wrap(qubic));
            }

        }
        if(args.length > 1) {
            for(String qubic: args[1].split(split)) {
                this.attach(TryteBuffer.wrap(qubic));
            }
        }

        if(args.length > 2) {
            TritBuffer tb = TryteBuffer.wrap(args[2]);
            for(int numberOfEffects = tb.getPositiveInteger(); numberOfEffects-- > 0;) {
                int effectLength;
                int delay[];
                Environment env[];
                TritBuffer effectBuffer;

                effectLength = tb.getPositiveInteger();
                effectBuffer = TrintBuffer.wrap(tb.get(effectLength));

                env = new Environment[tb.getPositiveInteger()];
                delay = new int[env.length];

                for(int envIndex = 0; envIndex < env.length; envIndex++) {
                    delay[envIndex] = tb.getPositiveInteger();
                    String envHash = tb.getHash();
                    Environment e = environments.get(envHash);
                    if(e == null) {
                        e = new Environment();
                        environments.put(envHash, e);
                    }
                    env[envIndex] = e;
                    e.enqueue(effectBuffer, delay[envIndex]);
                }
            }
            /*
            Queue<Map<String, List<String>>> quantList;
            Map<String, List<String>> stringMap;
            List<String> processingEffects;
            for (String quant : args[1].split(spquant)) {
                quantList = new LinkedList<>();
                stringMap = new TreeMap<>();
                processingEffects = Arrays.asList(quant.split(split));
                for (String effect : processingEffects) {
                    stringMap.getOrDefault(effect.substring(0, ENV_LENGTH), new LinkedList<>()).add(effect.substring(ENV_LENGTH));
                }
                quantList.add(stringMap);
                effects.add(quantList);
            }
            */
        }
    }

    public void attach(TritBuffer qubic) throws Exception {
        Knot[] knots;
        Entity entity;
        Environment environment;
        String envHash;

        knots = qubics.get(qubic.getHash());
        for(int i = qubic.getPositiveInteger(); i-- > 0;) {
            entity = new Entity();
            entity.branch = knots[qubic.getPositiveInteger()];
            for(int j = qubic.getPositiveInteger(); j-- > 0;) {
                envHash = qubic.getHash();
                environment = environments.get(envHash);
                if(environment == null) {
                    environment = new Environment();
                    environments.put(envHash, environment);
                }
                environment.add(entity,
                        qubic.getPositiveInteger(),
                        qubic.getPositiveInteger(),
                        qubic.getPositiveInteger());
            }
            for(int j = qubic.getPositiveInteger(); j-- > 0;) {
                envHash = qubic.getHash();
                environment = environments.get(envHash);
                if(environment == null) {
                    environment = new Environment();
                    environments.put(envHash, environment);
                }
                entity.add(environment,
                        qubic.getPositiveInteger(),
                        qubic.getPositiveInteger(),
                        qubic.getPositiveInteger());
            }
        }
    }

    private void parse(TritBuffer qTrits) throws Exception {
        int ver;
        String hash;
        List<Knot> knots, branches;

        knots = new ArrayList<>();
        branches = new LinkedList<>();
        hash = qTrits.getHash();

        ver = qTrits.getPositiveInteger();
        if(ver != 0) {
            throw new Exception("Bad Abra version");
        }

        knots.addAll(parseExternalBranches(qTrits));

        knots.addAll(parseLuts(qTrits));

        for(int i = qTrits.getPositiveInteger(); i-- > 0; ) {
            Branch b;
            b = new Branch(new int[qTrits.getPositiveInteger()]);
            for(int j = b.inputLength.length; j-- > 0;) {
                b.inputLength[j] = qTrits.getPositiveInteger();
            }
            b.body = new Site[qTrits.getPositiveInteger()];
            b.output = new Site[qTrits.getPositiveInteger()];
            b.latch = new Site[qTrits.getPositiveInteger()];
            parseSite(b.body, qTrits);
            parseSite(b.output, qTrits);
            parseSite(b.latch, qTrits);
            branches.add(b);
        }

        knots.addAll(branches);

        for(Knot k: branches) {
            Branch b = (Branch) k;
            setSiteKnot(b.body, knots);
            setSiteKnot(b.output, knots);
            setSiteKnot(b.latch, knots);
            for(Site s: b.latch) {
                s.latch = true;
            }
        }

        qubics.putIfAbsent(hash, knots.toArray(new Knot[]{}));
    }

    private List<Knot> parseExternalBranches(TritBuffer qTrits) throws IOException {
        Knot[] branches;
        List<Knot> knots = new LinkedList<>();
        for(int i = qTrits.getPositiveInteger(); i-- > 0;) {
            branches = qubics.get(qTrits.getHash());
            for(int j = 0, nImport = qTrits.getPositiveInteger(); j < nImport; j++) {
                knots.add(branches[qTrits.getPositiveInteger()]);
            }
        }
        return knots;
    }

    private List<Knot> parseLuts(TritBuffer qTrits) throws IOException {
        List<Knot> knots = new LinkedList<>();
        for(int i = qTrits.getPositiveInteger(); i-- > 0; ) {
            Lut l = new Lut();
            for(int j = 9; j-- > 0;) {
                l.addRow(qTrits.getQuad());
            }
            knots.add(l);
        }
        return knots;
    }

    private void setSiteKnot(Site[] ss, List<Knot> knots) {
        for(Site s: ss) {
            if(s.knotIndex > 0) {
                s.knot = knots.get(s.knotIndex - 1);
            }
        }
    }

    private void parseSite(Site[] s, TritBuffer b) throws IOException {
        for(int j = 0; j < s.length; j++) {
            s[j] = new Site();
            s[j].knotIndex = b.getPositiveInteger();
            s[j].inputs = new int[b.getPositiveInteger()];
            for(int k = 0; k < s[j].inputs.length; k++) {
                s[j].inputs[k] = b.getPositiveInteger();
            }
        }
    }

    public void schedule(Environment env, Effect effect) {

    }
}
