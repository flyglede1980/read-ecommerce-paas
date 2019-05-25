package net.yitun.basic.utils;

/**
 * <pre>
 * <b>密码 工具.</b>
 * <b>Description:</b>
 * 1、检测密码强度；
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年9月9日 下午8:44:15 LH
 *         new file.
 * </pre>
 */
public abstract class PwdUtil {

    // Level（级别）
    // 0-3 : [easy]
    // 4-6 : [midium]
    // 7-9 : [strong]
    // 10-12 : [very_strong]
    // >12 : [extremely_strong]
    // 简单: EASY, 中等: MIDIUM, 强壮: STRONG, 非常强壮: VERY_STRONG, 超级强壮: EXTREMELY_STRONG
    public enum LEVEL {
        EASY, MIDIUM, STRONG, VERY_STRONG, EXTREMELY_STRONG
    }

    private static final int NUM = 1;
    private static final int SMALL_LETTER = 2;
    private static final int CAPITAL_LETTER = 3;
    private static final int OTHER_CHAR = 4;

    // Simple password dictionary
    private static final String[] DICT = { "password", "abc123", "iloveyou", "adobe123", "123123", "sunshine", "1314520",
            "a1b2c3", "123qwe", "aaa111", "qweasd", "admin", "passwd", "123456", "111111", "abcdef", "abcabc" };

    public static void main(String[] args) {
//        String passwd = "123456";
//        passwd = "2hAj5#mne-ix.86H";
//        System.out.println(PwdUtil.level(passwd));
//        System.out.println(new String(B64Util.encode("YiT@jwtAuth")));
//        System.out.println(new String(B64Util.encode("YiT@PassSlat")));
    }

    /**
     * Get password strength level, includes easy, midium, strong, very strong, extremely strong
     *
     * @param passwd
     * @return
     */
    public static LEVEL level(String passwd) {
        int level = check(passwd);
        switch (level) {
        case 0:
        case 1:
        case 2:
        case 3:
            return LEVEL.EASY;
        case 4:
        case 5:
        case 6:
            return LEVEL.MIDIUM;
        case 7:
        case 8:
        case 9:
            return LEVEL.STRONG;
        case 10:
        case 11:
        case 12:
            return LEVEL.VERY_STRONG;
        default:
            return LEVEL.EXTREMELY_STRONG;
        }
    }

    /**
     * Check password's strength
     *
     * @param passwd
     * @return strength level
     */
    public static int check(String passwd) {
        if (equalsNull(passwd))
            return 0;
//            throw new IllegalArgumentException("password is empty");

        int level = 0;
        int len = passwd.length();

        // increase points
        if (countLetter(passwd, NUM) > 0)
            level++;

        if (countLetter(passwd, SMALL_LETTER) > 0)
            level++;

        if (len > 4 && countLetter(passwd, CAPITAL_LETTER) > 0)
            level++;

        if (len > 6 && countLetter(passwd, OTHER_CHAR) > 0)
            level++;

        if (len > 4 && countLetter(passwd, NUM) > 0 && countLetter(passwd, SMALL_LETTER) > 0
                || countLetter(passwd, NUM) > 0 && countLetter(passwd, CAPITAL_LETTER) > 0
                || countLetter(passwd, NUM) > 0 && countLetter(passwd, OTHER_CHAR) > 0
                || countLetter(passwd, SMALL_LETTER) > 0 && countLetter(passwd, CAPITAL_LETTER) > 0
                || countLetter(passwd, SMALL_LETTER) > 0 && countLetter(passwd, OTHER_CHAR) > 0
                || countLetter(passwd, CAPITAL_LETTER) > 0 && countLetter(passwd, OTHER_CHAR) > 0)
            level++;

        if (len > 6 && countLetter(passwd, NUM) > 0 && countLetter(passwd, SMALL_LETTER) > 0
                && countLetter(passwd, CAPITAL_LETTER) > 0
                || countLetter(passwd, NUM) > 0 && countLetter(passwd, SMALL_LETTER) > 0 && countLetter(passwd, OTHER_CHAR) > 0
                || countLetter(passwd, NUM) > 0 && countLetter(passwd, CAPITAL_LETTER) > 0
                        && countLetter(passwd, OTHER_CHAR) > 0
                || countLetter(passwd, SMALL_LETTER) > 0 && countLetter(passwd, CAPITAL_LETTER) > 0
                        && countLetter(passwd, OTHER_CHAR) > 0)
            level++;

        if (len > 8 && countLetter(passwd, NUM) > 0 && countLetter(passwd, SMALL_LETTER) > 0
                && countLetter(passwd, CAPITAL_LETTER) > 0 && countLetter(passwd, OTHER_CHAR) > 0)
            level++;

        if (len > 6 && countLetter(passwd, NUM) >= 3 && countLetter(passwd, SMALL_LETTER) >= 3
                || countLetter(passwd, NUM) >= 3 && countLetter(passwd, CAPITAL_LETTER) >= 3
                || countLetter(passwd, NUM) >= 3 && countLetter(passwd, OTHER_CHAR) >= 2
                || countLetter(passwd, SMALL_LETTER) >= 3 && countLetter(passwd, CAPITAL_LETTER) >= 3
                || countLetter(passwd, SMALL_LETTER) >= 3 && countLetter(passwd, OTHER_CHAR) >= 2
                || countLetter(passwd, CAPITAL_LETTER) >= 3 && countLetter(passwd, OTHER_CHAR) >= 2)
            level++;

        if (len > 8 && countLetter(passwd, NUM) >= 2 && countLetter(passwd, SMALL_LETTER) >= 2
                && countLetter(passwd, CAPITAL_LETTER) >= 2
                || countLetter(passwd, NUM) >= 2 && countLetter(passwd, SMALL_LETTER) >= 2
                        && countLetter(passwd, OTHER_CHAR) >= 2
                || countLetter(passwd, NUM) >= 2 && countLetter(passwd, CAPITAL_LETTER) >= 2
                        && countLetter(passwd, OTHER_CHAR) >= 2
                || countLetter(passwd, SMALL_LETTER) >= 2 && countLetter(passwd, CAPITAL_LETTER) >= 2
                        && countLetter(passwd, OTHER_CHAR) >= 2)
            level++;

        if (len > 10 && countLetter(passwd, NUM) >= 2 && countLetter(passwd, SMALL_LETTER) >= 2
                && countLetter(passwd, CAPITAL_LETTER) >= 2 && countLetter(passwd, OTHER_CHAR) >= 2)
            level++;

        if (countLetter(passwd, OTHER_CHAR) >= 3)
            level++;

        if (countLetter(passwd, OTHER_CHAR) >= 6)
            level++;

        if (len > 12) {
            level++;
            if (len >= 16)
                level++;
        }

        // decrease points
        if ("abcdefghijklmnopqrstuvwxyz".indexOf(passwd) > 0 || "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(passwd) > 0)
            level--;

        if ("qwertyuiop".indexOf(passwd) > 0 || "asdfghjkl".indexOf(passwd) > 0 || "zxcvbnm".indexOf(passwd) > 0)
            level--;

        if (isNumeric(passwd) && ("01234567890".indexOf(passwd) > 0 || "09876543210".indexOf(passwd) > 0))
            level--;

        if (countLetter(passwd, NUM) == len || countLetter(passwd, SMALL_LETTER) == len
                || countLetter(passwd, CAPITAL_LETTER) == len)
            level--;

        if (len % 2 == 0) { // aaabbb
            String part1 = passwd.substring(0, len / 2);
            String part2 = passwd.substring(len / 2);
            if (part1.equals(part2))
                level--;

            if (isCharEqual(part1) && isCharEqual(part2))
                level--;

        }
        if (len % 3 == 0) { // ababab
            String part1 = passwd.substring(0, len / 3);
            String part2 = passwd.substring(len / 3, len / 3 * 2);
            String part3 = passwd.substring(len / 3 * 2);
            if (part1.equals(part2) && part2.equals(part3))
                level--;

        }

        if (isNumeric(passwd) && len >= 6) { // 19881010 or 881010
            int year = 0;
            if (len == 8 || len == 6)
                year = Integer.parseInt(passwd.substring(0, len - 4));

            int size = sizeOfInt(year);
            int month = Integer.parseInt(passwd.substring(size, size + 2));
            int day = Integer.parseInt(passwd.substring(size + 2, len));
            if (year >= 1950 && year < 2050 && month >= 1 && month <= 12 && day >= 1 && day <= 31)
                level--;

        }

        if (null != DICT && DICT.length > 0) // dictionary
            for (int i = 0; i < DICT.length; i++)
                if (passwd.equals(DICT[i]) || DICT[i].indexOf(passwd) >= 0) {
                    level--;
                    break;
                }

        if (len <= 6) {
            level--;
            if (len <= 4) {
                level--;
                if (len <= 3)
                    level = 0;
            }
        }

        if (isCharEqual(passwd))
            level = 0;

        if (level < 0)
            level = 0;

        return level;
    }

    /**
     * Check character's type, includes num, capital letter, small letter and other character.
     *
     * @param c
     * @return
     */
    private static int checkType(char c) {
        if (c >= 48 && c <= 57) {
            return NUM;
        }
        if (c >= 65 && c <= 90) {
            return CAPITAL_LETTER;
        }
        if (c >= 97 && c <= 122) {
            return SMALL_LETTER;
        }
        return OTHER_CHAR;
    }

    /**
     * Count password's number by different type
     *
     * @param passwd
     * @param type
     * @return
     */
    private static int countLetter(String passwd, int type) {
        int count = 0;
        if (null != passwd && passwd.length() > 0)
            for (char c : passwd.toCharArray())
                if (checkType(c) == type)
                    count++;
        return count;
    }

    // =========================================================================================
    private static final int[] SIZE_TABLE = { 9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999,
            Integer.MAX_VALUE };

    /**
     * calculate the size of an integer number
     *
     * @param x
     * @return
     */
    private static int sizeOfInt(int x) {
        for (int i = 0;; i++)
            if (x <= SIZE_TABLE[i])
                return i + 1;
    }

    /**
     * Judge whether the string is whitespace, empty ("") or null.
     *
     * @param DICT
     * @return
     */
    private static boolean equalsNull(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0 || str.equalsIgnoreCase("null"))
            return true;

        for (int i = 0; i < strLen; i++)
            if ((Character.isWhitespace(str.charAt(i)) == false))
                return false;

        return true;
    }

    /**
     * Determines if the string is a digit
     *
     * @param DICT
     * @return
     */
    private static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0;)
            if (!Character.isDigit(str.charAt(i)))
                return false;

        return true;
    }

    /**
     * Judge whether each character of the string equals
     *
     * @param DICT
     * @return
     */
    private static boolean isCharEqual(String str) {
        return str.replace(str.charAt(0), ' ').trim().length() == 0;
    }

}
