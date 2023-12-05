package com.jcraft.jsch;

import com.jcraft.jsch.IdentityRepository;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class JSch {
    private static final Logger DEVNULL = new Logger() {
        public boolean isEnabled(int i) {
            return false;
        }

        public void log(int i, String str) {
        }
    };
    public static final String VERSION = "0.1.54";
    static Hashtable config = new Hashtable();
    static Logger logger = DEVNULL;
    private ConfigRepository configRepository = null;
    private IdentityRepository defaultIdentityRepository = new LocalIdentityRepository(this);
    private IdentityRepository identityRepository = this.defaultIdentityRepository;
    private HostKeyRepository known_hosts = null;
    private Vector sessionPool = new Vector();

    static {
        config.put("kex", "ecdh-sha2-nistp256,ecdh-sha2-nistp384,ecdh-sha2-nistp521,diffie-hellman-group14-sha1,diffie-hellman-group-exchange-sha256,diffie-hellman-group-exchange-sha1,diffie-hellman-group1-sha1");
        config.put("server_host_key", "ssh-rsa,ssh-dss,ecdsa-sha2-nistp256,ecdsa-sha2-nistp384,ecdsa-sha2-nistp521");
        config.put("cipher.s2c", "aes128-ctr,aes128-cbc,3des-ctr,3des-cbc,blowfish-cbc,aes192-ctr,aes192-cbc,aes256-ctr,aes256-cbc");
        config.put("cipher.c2s", "aes128-ctr,aes128-cbc,3des-ctr,3des-cbc,blowfish-cbc,aes192-ctr,aes192-cbc,aes256-ctr,aes256-cbc");
        config.put("mac.s2c", "hmac-md5,hmac-sha1,hmac-sha2-256,hmac-sha1-96,hmac-md5-96");
        config.put("mac.c2s", "hmac-md5,hmac-sha1,hmac-sha2-256,hmac-sha1-96,hmac-md5-96");
        config.put("compression.s2c", "none");
        config.put("compression.c2s", "none");
        config.put("lang.s2c", "");
        config.put("lang.c2s", "");
        config.put("compression_level", "6");
        config.put("diffie-hellman-group-exchange-sha1", "com.jcraft.jsch.DHGEX");
        config.put("diffie-hellman-group1-sha1", "com.jcraft.jsch.DHG1");
        config.put("diffie-hellman-group14-sha1", "com.jcraft.jsch.DHG14");
        config.put("diffie-hellman-group-exchange-sha256", "com.jcraft.jsch.DHGEX256");
        config.put("ecdsa-sha2-nistp256", "com.jcraft.jsch.jce.SignatureECDSA");
        config.put("ecdsa-sha2-nistp384", "com.jcraft.jsch.jce.SignatureECDSA");
        config.put("ecdsa-sha2-nistp521", "com.jcraft.jsch.jce.SignatureECDSA");
        config.put("ecdh-sha2-nistp256", "com.jcraft.jsch.DHEC256");
        config.put("ecdh-sha2-nistp384", "com.jcraft.jsch.DHEC384");
        config.put("ecdh-sha2-nistp521", "com.jcraft.jsch.DHEC521");
        config.put("ecdh-sha2-nistp", "com.jcraft.jsch.jce.ECDHN");
        config.put("dh", "com.jcraft.jsch.jce.DH");
        config.put("3des-cbc", "com.jcraft.jsch.jce.TripleDESCBC");
        config.put("blowfish-cbc", "com.jcraft.jsch.jce.BlowfishCBC");
        config.put("hmac-sha1", "com.jcraft.jsch.jce.HMACSHA1");
        config.put("hmac-sha1-96", "com.jcraft.jsch.jce.HMACSHA196");
        config.put("hmac-sha2-256", "com.jcraft.jsch.jce.HMACSHA256");
        config.put("hmac-md5", "com.jcraft.jsch.jce.HMACMD5");
        config.put("hmac-md5-96", "com.jcraft.jsch.jce.HMACMD596");
        config.put("sha-1", "com.jcraft.jsch.jce.SHA1");
        config.put("sha-256", "com.jcraft.jsch.jce.SHA256");
        config.put("sha-384", "com.jcraft.jsch.jce.SHA384");
        config.put("sha-512", "com.jcraft.jsch.jce.SHA512");
        config.put("md5", "com.jcraft.jsch.jce.MD5");
        config.put("signature.dss", "com.jcraft.jsch.jce.SignatureDSA");
        config.put("signature.rsa", "com.jcraft.jsch.jce.SignatureRSA");
        config.put("signature.ecdsa", "com.jcraft.jsch.jce.SignatureECDSA");
        config.put("keypairgen.dsa", "com.jcraft.jsch.jce.KeyPairGenDSA");
        config.put("keypairgen.rsa", "com.jcraft.jsch.jce.KeyPairGenRSA");
        config.put("keypairgen.ecdsa", "com.jcraft.jsch.jce.KeyPairGenECDSA");
        config.put("random", "com.jcraft.jsch.jce.Random");
        config.put("none", "com.jcraft.jsch.CipherNone");
        config.put("aes128-cbc", "com.jcraft.jsch.jce.AES128CBC");
        config.put("aes192-cbc", "com.jcraft.jsch.jce.AES192CBC");
        config.put("aes256-cbc", "com.jcraft.jsch.jce.AES256CBC");
        config.put("aes128-ctr", "com.jcraft.jsch.jce.AES128CTR");
        config.put("aes192-ctr", "com.jcraft.jsch.jce.AES192CTR");
        config.put("aes256-ctr", "com.jcraft.jsch.jce.AES256CTR");
        config.put("3des-ctr", "com.jcraft.jsch.jce.TripleDESCTR");
        config.put("arcfour", "com.jcraft.jsch.jce.ARCFOUR");
        config.put("arcfour128", "com.jcraft.jsch.jce.ARCFOUR128");
        config.put("arcfour256", "com.jcraft.jsch.jce.ARCFOUR256");
        config.put("userauth.none", "com.jcraft.jsch.UserAuthNone");
        config.put("userauth.password", "com.jcraft.jsch.UserAuthPassword");
        config.put("userauth.keyboard-interactive", "com.jcraft.jsch.UserAuthKeyboardInteractive");
        config.put("userauth.publickey", "com.jcraft.jsch.UserAuthPublicKey");
        config.put("userauth.gssapi-with-mic", "com.jcraft.jsch.UserAuthGSSAPIWithMIC");
        config.put("gssapi-with-mic.krb5", "com.jcraft.jsch.jgss.GSSContextKrb5");
        config.put("zlib", "com.jcraft.jsch.jcraft.Compression");
        config.put("zlib@openssh.com", "com.jcraft.jsch.jcraft.Compression");
        config.put("pbkdf", "com.jcraft.jsch.jce.PBKDF");
        config.put("StrictHostKeyChecking", "ask");
        config.put("HashKnownHosts", "no");
        config.put("PreferredAuthentications", "gssapi-with-mic,publickey,keyboard-interactive,password");
        config.put("CheckCiphers", "aes256-ctr,aes192-ctr,aes128-ctr,aes256-cbc,aes192-cbc,aes128-cbc,3des-ctr,arcfour,arcfour128,arcfour256");
        config.put("CheckKexes", "diffie-hellman-group14-sha1,ecdh-sha2-nistp256,ecdh-sha2-nistp384,ecdh-sha2-nistp521");
        config.put("CheckSignatures", "ecdsa-sha2-nistp256,ecdsa-sha2-nistp384,ecdsa-sha2-nistp521");
        config.put("MaxAuthTries", "6");
        config.put("ClearAllForwardings", "no");
    }

    public synchronized void setIdentityRepository(IdentityRepository identityRepository2) {
        if (identityRepository2 == null) {
            this.identityRepository = this.defaultIdentityRepository;
        } else {
            this.identityRepository = identityRepository2;
        }
    }

    public synchronized IdentityRepository getIdentityRepository() {
        return this.identityRepository;
    }

    public ConfigRepository getConfigRepository() {
        return this.configRepository;
    }

    public void setConfigRepository(ConfigRepository configRepository2) {
        this.configRepository = configRepository2;
    }

    public Session getSession(String str) throws JSchException {
        return getSession((String) null, str, 22);
    }

    public Session getSession(String str, String str2) throws JSchException {
        return getSession(str, str2, 22);
    }

    public Session getSession(String str, String str2, int i) throws JSchException {
        if (str2 != null) {
            return new Session(this, str, str2, i);
        }
        throw new JSchException("host must not be null.");
    }

    /* access modifiers changed from: protected */
    public void addSession(Session session) {
        synchronized (this.sessionPool) {
            this.sessionPool.addElement(session);
        }
    }

    /* access modifiers changed from: protected */
    public boolean removeSession(Session session) {
        boolean remove;
        synchronized (this.sessionPool) {
            remove = this.sessionPool.remove(session);
        }
        return remove;
    }

    public void setHostKeyRepository(HostKeyRepository hostKeyRepository) {
        this.known_hosts = hostKeyRepository;
    }

    public void setKnownHosts(String str) throws JSchException {
        if (this.known_hosts == null) {
            this.known_hosts = new KnownHosts(this);
        }
        HostKeyRepository hostKeyRepository = this.known_hosts;
        if (hostKeyRepository instanceof KnownHosts) {
            synchronized (hostKeyRepository) {
                ((KnownHosts) this.known_hosts).setKnownHosts(str);
            }
        }
    }

    public void setKnownHosts(InputStream inputStream) throws JSchException {
        if (this.known_hosts == null) {
            this.known_hosts = new KnownHosts(this);
        }
        HostKeyRepository hostKeyRepository = this.known_hosts;
        if (hostKeyRepository instanceof KnownHosts) {
            synchronized (hostKeyRepository) {
                ((KnownHosts) this.known_hosts).setKnownHosts(inputStream);
            }
        }
    }

    public HostKeyRepository getHostKeyRepository() {
        if (this.known_hosts == null) {
            this.known_hosts = new KnownHosts(this);
        }
        return this.known_hosts;
    }

    public void addIdentity(String str) throws JSchException {
        addIdentity(str, (byte[]) null);
    }

    public void addIdentity(String str, String str2) throws JSchException {
        byte[] str2byte = str2 != null ? Util.str2byte(str2) : null;
        addIdentity(str, str2byte);
        if (str2byte != null) {
            Util.bzero(str2byte);
        }
    }

    public void addIdentity(String str, byte[] bArr) throws JSchException {
        addIdentity((Identity) IdentityFile.newInstance(str, (String) null, this), bArr);
    }

    public void addIdentity(String str, String str2, byte[] bArr) throws JSchException {
        addIdentity((Identity) IdentityFile.newInstance(str, str2, this), bArr);
    }

    public void addIdentity(String str, byte[] bArr, byte[] bArr2, byte[] bArr3) throws JSchException {
        addIdentity((Identity) IdentityFile.newInstance(str, bArr, bArr2, this), bArr3);
    }

    public void addIdentity(Identity identity, byte[] bArr) throws JSchException {
        if (bArr != null) {
            try {
                byte[] bArr2 = new byte[bArr.length];
                System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                try {
                    identity.setPassphrase(bArr2);
                    Util.bzero(bArr2);
                } catch (Throwable th) {
                    th = th;
                    bArr = bArr2;
                    Util.bzero(bArr);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                Util.bzero(bArr);
                throw th;
            }
        }
        IdentityRepository identityRepository2 = this.identityRepository;
        if (identityRepository2 instanceof LocalIdentityRepository) {
            ((LocalIdentityRepository) identityRepository2).add(identity);
        } else if (!(identity instanceof IdentityFile) || identity.isEncrypted()) {
            synchronized (this) {
                if (!(this.identityRepository instanceof IdentityRepository.Wrapper)) {
                    setIdentityRepository(new IdentityRepository.Wrapper(this.identityRepository));
                }
            }
            ((IdentityRepository.Wrapper) this.identityRepository).add(identity);
        } else {
            this.identityRepository.add(((IdentityFile) identity).getKeyPair().forSSHAgent());
        }
    }

    public void removeIdentity(String str) throws JSchException {
        Vector identities = this.identityRepository.getIdentities();
        for (int i = 0; i < identities.size(); i++) {
            Identity identity = (Identity) identities.elementAt(i);
            if (identity.getName().equals(str)) {
                IdentityRepository identityRepository2 = this.identityRepository;
                if (identityRepository2 instanceof LocalIdentityRepository) {
                    ((LocalIdentityRepository) identityRepository2).remove(identity);
                } else {
                    identityRepository2.remove(identity.getPublicKeyBlob());
                }
            }
        }
    }

    public void removeIdentity(Identity identity) throws JSchException {
        this.identityRepository.remove(identity.getPublicKeyBlob());
    }

    public Vector getIdentityNames() throws JSchException {
        Vector vector = new Vector();
        Vector identities = this.identityRepository.getIdentities();
        for (int i = 0; i < identities.size(); i++) {
            vector.addElement(((Identity) identities.elementAt(i)).getName());
        }
        return vector;
    }

    public void removeAllIdentity() throws JSchException {
        this.identityRepository.removeAll();
    }

    public static String getConfig(String str) {
        String str2;
        synchronized (config) {
            str2 = (String) config.get(str);
        }
        return str2;
    }

    public static void setConfig(Hashtable hashtable) {
        synchronized (config) {
            Enumeration keys = hashtable.keys();
            while (keys.hasMoreElements()) {
                String str = (String) keys.nextElement();
                config.put(str, (String) hashtable.get(str));
            }
        }
    }

    public static void setConfig(String str, String str2) {
        config.put(str, str2);
    }

    public static void setLogger(Logger logger2) {
        if (logger2 == null) {
            logger2 = DEVNULL;
        }
        logger = logger2;
    }

    static Logger getLogger() {
        return logger;
    }
}
