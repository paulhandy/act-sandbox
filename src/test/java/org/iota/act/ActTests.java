package org.iota.act;

import org.junit.Test;

public class ActTests {
    static final String testHashOne = "ABCDEFGHIJKLMNOPQRSTUVWXYZ9ABCDEFGHIJKLMNOPQRSTUVWXYZ9ABCDEFGHIJKLMNOPQRSTUVWXYZ9";
    static final String aHash = "A99999999999999999999999999999999999999999999999999999999999999999999999999999999";
    static final String oneHash    = "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN";
    static final String testAttachmentOne =
            testHashOne + "AA" + aHash + "IC" + oneHash + "9";
    static final String testQubicOne =
            testHashOne + "9JUBCI9";
    static final String testEffectsOne =
            aHash + "EJH";
    @Test
    public void testBasic() throws Exception {
        Act act;

        act = new Act(new String[]{
                testQubicOne,
                testAttachmentOne,
                //testEffectsOne
        });

        TritBuffer b = TryteBuffer.wrap(aHash);
        Environment env = act.environments.get(b.getHash());
        Effect effect = new Effect(TryteBuffer.wrap("EJH"), new Environment[]{env}, new int[]{0});
        //act.schedule(env, effect);
        act.wave(env);
        //act.wave();
    }
}
