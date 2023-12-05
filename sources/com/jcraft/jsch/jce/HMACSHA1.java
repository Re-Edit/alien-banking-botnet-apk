package com.jcraft.jsch.jce;

public class HMACSHA1 extends HMAC {
    public HMACSHA1() {
        this.name = "hmac-sha1";
        this.bsize = 20;
        this.algorithm = "HmacSHA1";
    }
}
