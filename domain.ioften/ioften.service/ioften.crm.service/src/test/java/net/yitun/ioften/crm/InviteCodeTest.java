package net.yitun.ioften.crm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import net.yitun.basic.utils.IdUtil;

public class InviteCodeTest {

    static final char[] chars = { 'Q', 'W', 'E', '8', 'S', '2', 'D', 'Z', 'X', '9' //
            , 'C', '7', 'P', '5', 'K', '3', 'M', 'J', 'U', 'F', 'R', '4', 'V', 'Y', 'T', 'N', '6', 'B', 'G', 'H', 'A', 'L' };

    public static String code(int length) {
        Random random = new Random();
        char[] inviteChars = new char[length];
        for (int index = 0; index < length; index++)
            inviteChars[index] = chars[random.nextInt(chars.length)];

        return String.valueOf(inviteChars);
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 100000; i++)
            set.add(code(6));
        System.out.println(System.currentTimeMillis() - start);

        Iterator<String> iterator = set.iterator();
        for (int i = 0; i < 10; i++)
            System.out.println(iterator.next());

        System.out.println();
        System.out.println(set.size());
    }

    /**
     * 方式二 <br/>
     * 邀请码生成器，算法原理：<br/>
     * 1) 获取id: 1127738 <br/>
     * 2) 使用自定义进制转为：gpm6 <br/>
     * 3) 转为字符串，并在后面加'o'字符：gpm6o <br/>
     * 4）在后面随机产生若干个随机数字字符：gpm6o7 <br/>
     * 转为自定义进制后就不会出现o这个字符，然后在后面加个'o'，这样就能确定唯一性。最后在后面产生一些随机字符进行补全。<br/>
     * 
     * @author jiayu.qiu
     */
    /** 自定义进制(0,1没有加入,容易与o,l混淆) */
    static final char[] _chars = { '0', 'Q', 'W', 'E', '8', 'A', 'S', '2', 'D', 'Z', 'X', '9', 'C', '7', 'P', '5', 'I', 'K',
            '3', 'M', 'J', 'U', 'F', 'R', '4', 'V', 'Y', 'L', 'T', 'N', '6', 'B', 'G', 'H' };

    /** (不能与自定义进制有重复) */
    private static final char b = 'o';

    /** 进制长度 */
    private static final int binLen = _chars.length;

    /** 序列最小长度 */
    private static final int s = 6;

    /**
     * 根据ID生成六位随机码
     * 
     * @param id ID
     * @return 随机码
     */
    public static String toSerialCode(long id) {
        char[] buf = new char[32];
        int charPos = 32;

        while ((id / binLen) > 0) {
            int ind = (int) (id % binLen);
            // System.out.println(num + "-->" + ind);
            buf[--charPos] = _chars[ind];
            id /= binLen;
        }

        buf[--charPos] = _chars[(int) (id % binLen)];
        // System.out.println(num + "-->" + num % binLen);
        String str = new String(buf, charPos, (32 - charPos));
        // 不够长度的自动随机补全
        if (str.length() < s) {
            StringBuilder sb = new StringBuilder();
            sb.append(b);
            Random rnd = new Random();
            for (int i = 1; i < s - str.length(); i++)
                sb.append(_chars[rnd.nextInt(binLen)]);
            str += sb.toString();
        }

        return str;
    }

    public static long codeToId(String code) {
        long res = 0L;
        char chs[] = code.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            int ind = 0;
            for (int j = 0; j < binLen; j++)
                if (chs[i] == _chars[j]) {
                    ind = j;
                    break;
                }

            if (chs[i] == b)
                break;

            if (i > 0)
                res = res * binLen + ind;

            else
                res = ind;
            // System.out.println(ind + "-->" + res);
        }

        return res;
    }

    public static void main1(String[] args) {
        int size = 100;
        Set<String> codes = new HashSet<>(size);
        for (int i = 0; i < size; i++)
            codes.add(InviteCodeTest.toSerialCode(IdUtil.id()));

        System.out.println(codes.size());
        System.out.println(InviteCodeTest.toSerialCode(IdUtil.MIN));
    }
}