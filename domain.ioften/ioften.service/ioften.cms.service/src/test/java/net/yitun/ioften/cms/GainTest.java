package net.yitun.ioften.cms;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

public class GainTest {

    @Test
    public void calcGain() {
        Long[] cardinals = { 1000L, 1234L };
        for (int i = 0; i < 100; i++) {
            long cardinal = RandomUtils.nextLong(cardinals[0], cardinals[1]);
            System.out.println(cardinal);
        }

    }
}
