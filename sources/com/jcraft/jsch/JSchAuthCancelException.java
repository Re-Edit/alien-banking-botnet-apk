package com.jcraft.jsch;

class JSchAuthCancelException extends JSchException {
    String method;

    JSchAuthCancelException() {
    }

    JSchAuthCancelException(String str) {
        super(str);
        this.method = str;
    }

    public String getMethod() {
        return this.method;
    }
}
