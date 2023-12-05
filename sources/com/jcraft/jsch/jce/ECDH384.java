package com.jcraft.jsch.jce;

import com.jcraft.jsch.ECDH;

public class ECDH384 extends ECDHN implements ECDH {
    public void init() throws Exception {
        super.init(384);
    }
}
