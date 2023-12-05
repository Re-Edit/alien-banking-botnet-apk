package com.jcraft.jsch;

public class JSchException extends Exception {
    private Throwable cause = null;

    public JSchException() {
    }

    public JSchException(String str) {
        super(str);
    }

    public JSchException(String str, Throwable th) {
        super(str);
        this.cause = th;
    }

    public Throwable getCause() {
        return this.cause;
    }
}
