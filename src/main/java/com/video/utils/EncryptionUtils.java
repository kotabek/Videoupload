package com.video.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by kotabek on 4/20/17.
 */
public class EncryptionUtils {
    private static final String SALT = "THIS$PROJECT";

    public static String md5(String key) {
        if (key == null) {
            return null;
        }
        return DigestUtils.md5Hex(SALT + key);
    }

}
