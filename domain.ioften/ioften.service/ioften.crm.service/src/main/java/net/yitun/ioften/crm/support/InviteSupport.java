package net.yitun.ioften.crm.support;

import java.util.Random;

/**
 * <pre>
 * <b>用户 邀请支撑.</b>
 * <b>Description:</b>
 * 1、监听邀请注册, 如果是被邀请用户，需要为邀请用户派发挖矿奖励
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月27日 下午1:37:36 LH
 *         new file.
 * </pre>
 */
public class InviteSupport {

    private static final char[] chars = { 'Q', 'W', 'E', '8', 'S', '2', 'D', 'Z', 'X', '9' //
            , 'C', '7', 'P', '5', 'K', '3', 'M', 'J', 'U', 'F', 'R', '4', 'V', 'Y', 'T', 'N', '6', 'B', 'G', 'H', 'A', 'L' };

    /**
     * 生成邀请码算法
     * 
     * @param length 邀请码长度
     * @return String 邀请码
     */
    public static String genCode(int length) {
        Random random = new Random();
        char[] inviteChars = new char[length];
        for (int index = 0; index < length; index++)
            inviteChars[index] = chars[random.nextInt(chars.length)];

        return String.valueOf(inviteChars);
    }

}
