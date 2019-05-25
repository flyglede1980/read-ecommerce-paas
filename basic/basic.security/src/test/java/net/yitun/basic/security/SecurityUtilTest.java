package net.yitun.basic.security;

import org.junit.Test;

public class SecurityUtilTest {

    @Test
    public void encodePwd() {
        String pwd = "123456";
        System.out.println(SecurityUtil.encode(pwd));
    }
}
