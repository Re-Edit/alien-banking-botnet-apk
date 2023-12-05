package com.jcraft.jsch.jcraft;

import com.jcraft.jsch.MAC;
import java.security.MessageDigest;

public class HMACSHA1 extends HMAC implements MAC {
    private static final String name = "hmac-sha1";

    public String getName() {
        return name;
    }

    public HMACSHA1() {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (Exception e) {
            System.err.println(e);
            messageDigest = null;
        }
        setH(messageDigest);
    }
}
