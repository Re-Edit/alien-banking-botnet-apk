package com.jcraft.jsch;

import java.util.Vector;

public interface IdentityRepository {
    public static final int NOTRUNNING = 1;
    public static final int RUNNING = 2;
    public static final int UNAVAILABLE = 0;

    boolean add(byte[] bArr);

    Vector getIdentities();

    String getName();

    int getStatus();

    boolean remove(byte[] bArr);

    void removeAll();

    public static class Wrapper implements IdentityRepository {
        private Vector cache;
        private IdentityRepository ir;
        private boolean keep_in_cache;

        Wrapper(IdentityRepository identityRepository) {
            this(identityRepository, false);
        }

        Wrapper(IdentityRepository identityRepository, boolean z) {
            this.cache = new Vector();
            this.keep_in_cache = false;
            this.ir = identityRepository;
            this.keep_in_cache = z;
        }

        public String getName() {
            return this.ir.getName();
        }

        public int getStatus() {
            return this.ir.getStatus();
        }

        public boolean add(byte[] bArr) {
            return this.ir.add(bArr);
        }

        public boolean remove(byte[] bArr) {
            return this.ir.remove(bArr);
        }

        public void removeAll() {
            this.cache.removeAllElements();
            this.ir.removeAll();
        }

        public Vector getIdentities() {
            Vector vector = new Vector();
            for (int i = 0; i < this.cache.size(); i++) {
                vector.add((Identity) this.cache.elementAt(i));
            }
            Vector identities = this.ir.getIdentities();
            for (int i2 = 0; i2 < identities.size(); i2++) {
                vector.add(identities.elementAt(i2));
            }
            return vector;
        }

        /* access modifiers changed from: package-private */
        public void add(Identity identity) {
            if (this.keep_in_cache || identity.isEncrypted() || !(identity instanceof IdentityFile)) {
                this.cache.addElement(identity);
                return;
            }
            try {
                this.ir.add(((IdentityFile) identity).getKeyPair().forSSHAgent());
            } catch (JSchException unused) {
            }
        }

        /* access modifiers changed from: package-private */
        public void check() {
            if (this.cache.size() > 0) {
                Object[] array = this.cache.toArray();
                for (Object obj : array) {
                    Identity identity = (Identity) obj;
                    this.cache.removeElement(identity);
                    add(identity);
                }
            }
        }
    }
}
