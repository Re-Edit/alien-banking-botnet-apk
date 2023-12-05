package com.jcraft.jsch.jce;

public class HMACSHA512 extends HMAC {
    public HMACSHA512() {
        this.name = "hmac-sha2-512";
        this.bsize = 64;
        this.algorithm = "HmacSHA512";
    }
}
