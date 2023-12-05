package com.jcraft.jsch;

import java.util.Vector;

class LocalIdentityRepository implements IdentityRepository {
    private static final String name = "Local Identity Repository";
    private Vector identities = new Vector();
    private JSch jsch;

    public String getName() {
        return name;
    }

    public int getStatus() {
        return 2;
    }

    LocalIdentityRepository(JSch jSch) {
        this.jsch = jSch;
    }

    public synchronized Vector getIdentities() {
        Vector vector;
        removeDupulicates();
        vector = new Vector();
        for (int i = 0; i < this.identities.size(); i++) {
            vector.addElement(this.identities.elementAt(i));
        }
        return vector;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004c, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0056, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void add(com.jcraft.jsch.Identity r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            java.util.Vector r0 = r4.identities     // Catch:{ all -> 0x0057 }
            boolean r0 = r0.contains(r5)     // Catch:{ all -> 0x0057 }
            if (r0 != 0) goto L_0x0055
            byte[] r0 = r5.getPublicKeyBlob()     // Catch:{ all -> 0x0057 }
            if (r0 != 0) goto L_0x0016
            java.util.Vector r0 = r4.identities     // Catch:{ all -> 0x0057 }
            r0.addElement(r5)     // Catch:{ all -> 0x0057 }
            monitor-exit(r4)
            return
        L_0x0016:
            r1 = 0
        L_0x0017:
            java.util.Vector r2 = r4.identities     // Catch:{ all -> 0x0057 }
            int r2 = r2.size()     // Catch:{ all -> 0x0057 }
            if (r1 >= r2) goto L_0x0050
            java.util.Vector r2 = r4.identities     // Catch:{ all -> 0x0057 }
            java.lang.Object r2 = r2.elementAt(r1)     // Catch:{ all -> 0x0057 }
            com.jcraft.jsch.Identity r2 = (com.jcraft.jsch.Identity) r2     // Catch:{ all -> 0x0057 }
            byte[] r2 = r2.getPublicKeyBlob()     // Catch:{ all -> 0x0057 }
            if (r2 == 0) goto L_0x004d
            boolean r3 = com.jcraft.jsch.Util.array_equals(r0, r2)     // Catch:{ all -> 0x0057 }
            if (r3 == 0) goto L_0x004d
            boolean r3 = r5.isEncrypted()     // Catch:{ all -> 0x0057 }
            if (r3 != 0) goto L_0x004b
            java.util.Vector r3 = r4.identities     // Catch:{ all -> 0x0057 }
            java.lang.Object r3 = r3.elementAt(r1)     // Catch:{ all -> 0x0057 }
            com.jcraft.jsch.Identity r3 = (com.jcraft.jsch.Identity) r3     // Catch:{ all -> 0x0057 }
            boolean r3 = r3.isEncrypted()     // Catch:{ all -> 0x0057 }
            if (r3 == 0) goto L_0x004b
            r4.remove((byte[]) r2)     // Catch:{ all -> 0x0057 }
            goto L_0x004d
        L_0x004b:
            monitor-exit(r4)
            return
        L_0x004d:
            int r1 = r1 + 1
            goto L_0x0017
        L_0x0050:
            java.util.Vector r0 = r4.identities     // Catch:{ all -> 0x0057 }
            r0.addElement(r5)     // Catch:{ all -> 0x0057 }
        L_0x0055:
            monitor-exit(r4)
            return
        L_0x0057:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.LocalIdentityRepository.add(com.jcraft.jsch.Identity):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0015, code lost:
        return false;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean add(byte[] r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            java.lang.String r0 = "from remote:"
            r1 = 0
            com.jcraft.jsch.JSch r2 = r3.jsch     // Catch:{ JSchException -> 0x0013, all -> 0x0010 }
            com.jcraft.jsch.IdentityFile r4 = com.jcraft.jsch.IdentityFile.newInstance(r0, r4, r1, r2)     // Catch:{ JSchException -> 0x0013, all -> 0x0010 }
            r3.add((com.jcraft.jsch.Identity) r4)     // Catch:{ JSchException -> 0x0013, all -> 0x0010 }
            r4 = 1
            monitor-exit(r3)
            return r4
        L_0x0010:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        L_0x0013:
            r4 = 0
            monitor-exit(r3)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.LocalIdentityRepository.add(byte[]):boolean");
    }

    /* access modifiers changed from: package-private */
    public synchronized void remove(Identity identity) {
        if (this.identities.contains(identity)) {
            this.identities.removeElement(identity);
            identity.clear();
        } else {
            remove(identity.getPublicKeyBlob());
        }
    }

    public synchronized boolean remove(byte[] bArr) {
        if (bArr == null) {
            return false;
        }
        for (int i = 0; i < this.identities.size(); i++) {
            Identity identity = (Identity) this.identities.elementAt(i);
            byte[] publicKeyBlob = identity.getPublicKeyBlob();
            if (publicKeyBlob != null) {
                if (Util.array_equals(bArr, publicKeyBlob)) {
                    this.identities.removeElement(identity);
                    identity.clear();
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized void removeAll() {
        for (int i = 0; i < this.identities.size(); i++) {
            ((Identity) this.identities.elementAt(i)).clear();
        }
        this.identities.removeAllElements();
    }

    private void removeDupulicates() {
        Vector vector = new Vector();
        int size = this.identities.size();
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                Identity identity = (Identity) this.identities.elementAt(i);
                byte[] publicKeyBlob = identity.getPublicKeyBlob();
                if (publicKeyBlob != null) {
                    int i2 = i + 1;
                    while (true) {
                        if (i2 >= size) {
                            break;
                        }
                        Identity identity2 = (Identity) this.identities.elementAt(i2);
                        byte[] publicKeyBlob2 = identity2.getPublicKeyBlob();
                        if (publicKeyBlob2 != null && Util.array_equals(publicKeyBlob, publicKeyBlob2) && identity.isEncrypted() == identity2.isEncrypted()) {
                            vector.addElement(publicKeyBlob);
                            break;
                        }
                        i2++;
                    }
                }
            }
            for (int i3 = 0; i3 < vector.size(); i3++) {
                remove((byte[]) vector.elementAt(i3));
            }
        }
    }
}
