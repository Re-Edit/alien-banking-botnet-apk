package com.jcraft.jsch;

class IdentityFile implements Identity {
    private String identity;
    private JSch jsch;
    private KeyPair kpair;

    static IdentityFile newInstance(String str, String str2, JSch jSch) throws JSchException {
        return new IdentityFile(jSch, str, KeyPair.load(jSch, str, str2));
    }

    static IdentityFile newInstance(String str, byte[] bArr, byte[] bArr2, JSch jSch) throws JSchException {
        return new IdentityFile(jSch, str, KeyPair.load(jSch, bArr, bArr2));
    }

    private IdentityFile(JSch jSch, String str, KeyPair keyPair) throws JSchException {
        this.jsch = jSch;
        this.identity = str;
        this.kpair = keyPair;
    }

    public boolean setPassphrase(byte[] bArr) throws JSchException {
        return this.kpair.decrypt(bArr);
    }

    public byte[] getPublicKeyBlob() {
        return this.kpair.getPublicKeyBlob();
    }

    public byte[] getSignature(byte[] bArr) {
        return this.kpair.getSignature(bArr);
    }

    public boolean decrypt() {
        throw new RuntimeException("not implemented");
    }

    public String getAlgName() {
        return new String(this.kpair.getKeyTypeName());
    }

    public String getName() {
        return this.identity;
    }

    public boolean isEncrypted() {
        return this.kpair.isEncrypted();
    }

    public void clear() {
        this.kpair.dispose();
        this.kpair = null;
    }

    public KeyPair getKeyPair() {
        return this.kpair;
    }
}
