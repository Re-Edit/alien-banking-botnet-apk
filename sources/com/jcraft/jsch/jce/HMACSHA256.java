package com.jcraft.jsch.jce;

public class HMACSHA256 extends HMAC {
    public HMACSHA256() {
        this.name = "hmac-sha2-256";
        this.bsize = 32;
        this.algorithm = "HmacSHA256";
    }
}
