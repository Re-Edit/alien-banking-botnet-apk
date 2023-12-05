package com.jcraft.jsch.jcraft;

import com.jcraft.jsch.MAC;
import java.security.MessageDigest;

public class HMACMD5 extends HMAC implements MAC {
    private static final String name = "hmac-md5";

    public String getName() {
        return name;
    }

    public HMACMD5() {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.err.println(e);
            messageDigest = null;
        }
        setH(messageDigest);
    }
}
