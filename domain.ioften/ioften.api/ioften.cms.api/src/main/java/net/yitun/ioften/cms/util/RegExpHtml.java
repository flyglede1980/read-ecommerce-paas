package net.yitun.ioften.cms.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/9/10 0010.
 */
public class RegExpHtml {

    public static List<String> pickObs(String content) {
        // 用来存储获取到的图片地址
        List<String> srcList = new ArrayList<String>();

        Pattern p = Pattern.compile("<(img|IMG)(.*?)(>|></img>|/>)"); // 匹配字符串中的img标签
        Matcher matcher = p.matcher(content);

        // 判断是否含有图片
        boolean hasPic = matcher.find();
        // 如果含有图片，那么持续进行查找，直到匹配不到
        while (hasPic) {
            String group = matcher.group(2);// 获取第二个分组的内容，也就是 (.*?)匹配到的
            Pattern srcText = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\\?cjobs=true\"|\\?cjobs=true\')");// 匹配图片的地址
            Matcher _matcher = srcText.matcher(group);
            if (_matcher.find())
                srcList.add(_matcher.group(3));// 把获取到的图片地址添加到列表中

            hasPic = matcher.find();// 判断是否还有img标签
        }

        return srcList;
    }


    public static List<String> pick(String content) {
        // 用来存储获取到的图片地址
        List<String> srcList = new ArrayList<String>();

        Pattern p = Pattern.compile("<(img|IMG)(.*?)(>|></img>|/>)"); // 匹配字符串中的img标签
        Matcher matcher = p.matcher(content);

        // 判断是否含有图片
        boolean hasPic = matcher.find();
        // 如果含有图片，那么持续进行查找，直到匹配不到
        while (hasPic) {
            String group = matcher.group(2);// 获取第二个分组的内容，也就是 (.*?)匹配到的
            Pattern srcText = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");// 匹配图片的地址
            Matcher _matcher = srcText.matcher(group);
            if (_matcher.find())
                srcList.add(_matcher.group(3));// 把获取到的图片地址添加到列表中

            hasPic = matcher.find();// 判断是否还有img标签
        }

        return srcList;
    }

}
