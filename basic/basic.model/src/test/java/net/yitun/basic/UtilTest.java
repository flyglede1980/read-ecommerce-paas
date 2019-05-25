package net.yitun.basic;

import org.junit.Test;

public class UtilTest {

    @Test
    public void testMatche() {
        String phone = "13112345678";
        System.out.println(Util.matche(phone, Util.REGEX_PHONE));
    }
}
