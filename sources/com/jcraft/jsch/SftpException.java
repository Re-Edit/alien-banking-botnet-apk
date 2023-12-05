package com.jcraft.jsch;

public class SftpException extends Exception {
    private Throwable cause = null;
    public int id;

    public SftpException(int i, String str) {
        super(str);
        this.id = i;
    }

    public SftpException(int i, String str, Throwable th) {
        super(str);
        this.id = i;
        this.cause = th;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.id);
        stringBuffer.append(": ");
        stringBuffer.append(getMessage());
        return stringBuffer.toString();
    }

    public Throwable getCause() {
        return this.cause;
    }
}
