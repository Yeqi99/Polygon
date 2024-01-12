package cn.originmc.plugins.polygon.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicUtil {
    public static List<String> getPrefixList(String s) {
        if (!s.contains(" ")) {
            List<String> list = new ArrayList<>();
            list.add(s);
            return list;
        }
        return Arrays.asList(s.split(" "));
    }
}
