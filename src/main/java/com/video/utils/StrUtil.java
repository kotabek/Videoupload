package com.video.utils;

/**
 * Created by kotabek on 4/20/17.
 */
public class StrUtil {
    public static boolean isEmpty(String value) {
        return value == null
               || value.isEmpty()
               || value.trim().isEmpty()
               || value.equalsIgnoreCase("null");
    }
}
