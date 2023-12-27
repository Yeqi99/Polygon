package cn.originmc.plugins.polygon.utils;

import java.util.Arrays;
import java.util.List;

public class BasicUtil {
    public static List<String> getPrefixList(String s) {
        return Arrays.asList(s.split(" "));
    }
}
