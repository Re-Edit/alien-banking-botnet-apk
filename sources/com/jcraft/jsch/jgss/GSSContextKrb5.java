package com.jcraft.jsch.jgss;

import com.jcraft.jsch.GSSContext;
import com.jcraft.jsch.JSchException;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.MessageProp;

public class GSSContextKrb5 implements GSSContext {
    private static final String pUseSubjectCredsOnly = "javax.security.auth.useSubjectCredsOnly";
    private static String useSubjectCredsOnly = getSystemProperty(pUseSubjectCredsOnly);
    private org.ietf.jgss.GSSContext context = null;

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void create(java.lang.String r6, java.lang.String r7) throws com.jcraft.jsch.JSchException {
        /*
            r5 = this;
            org.ietf.jgss.Oid r6 = new org.ietf.jgss.Oid     // Catch:{ GSSException -> 0x0052 }
            java.lang.String r0 = "1.2.840.113554.1.2.2"
            r6.<init>(r0)     // Catch:{ GSSException -> 0x0052 }
            org.ietf.jgss.Oid r0 = new org.ietf.jgss.Oid     // Catch:{ GSSException -> 0x0052 }
            java.lang.String r1 = "1.2.840.113554.1.2.2.1"
            r0.<init>(r1)     // Catch:{ GSSException -> 0x0052 }
            org.ietf.jgss.GSSManager r1 = org.ietf.jgss.GSSManager.getInstance()     // Catch:{ GSSException -> 0x0052 }
            r2 = 0
            java.net.InetAddress r3 = java.net.InetAddress.getByName(r7)     // Catch:{ UnknownHostException -> 0x001b }
            java.lang.String r7 = r3.getCanonicalHostName()     // Catch:{ UnknownHostException -> 0x001b }
        L_0x001b:
            java.lang.StringBuffer r3 = new java.lang.StringBuffer     // Catch:{ GSSException -> 0x0052 }
            r3.<init>()     // Catch:{ GSSException -> 0x0052 }
            java.lang.String r4 = "host/"
            r3.append(r4)     // Catch:{ GSSException -> 0x0052 }
            r3.append(r7)     // Catch:{ GSSException -> 0x0052 }
            java.lang.String r7 = r3.toString()     // Catch:{ GSSException -> 0x0052 }
            org.ietf.jgss.GSSName r7 = r1.createName(r7, r0)     // Catch:{ GSSException -> 0x0052 }
            r0 = 0
            org.ietf.jgss.GSSContext r6 = r1.createContext(r7, r6, r2, r0)     // Catch:{ GSSException -> 0x0052 }
            r5.context = r6     // Catch:{ GSSException -> 0x0052 }
            org.ietf.jgss.GSSContext r6 = r5.context     // Catch:{ GSSException -> 0x0052 }
            r7 = 1
            r6.requestMutualAuth(r7)     // Catch:{ GSSException -> 0x0052 }
            org.ietf.jgss.GSSContext r6 = r5.context     // Catch:{ GSSException -> 0x0052 }
            r6.requestConf(r7)     // Catch:{ GSSException -> 0x0052 }
            org.ietf.jgss.GSSContext r6 = r5.context     // Catch:{ GSSException -> 0x0052 }
            r6.requestInteg(r7)     // Catch:{ GSSException -> 0x0052 }
            org.ietf.jgss.GSSContext r6 = r5.context     // Catch:{ GSSException -> 0x0052 }
            r6.requestCredDeleg(r7)     // Catch:{ GSSException -> 0x0052 }
            org.ietf.jgss.GSSContext r6 = r5.context     // Catch:{ GSSException -> 0x0052 }
            r6.requestAnonymity(r0)     // Catch:{ GSSException -> 0x0052 }
            return
        L_0x0052:
            r6 = move-exception
            com.jcraft.jsch.JSchException r7 = new com.jcraft.jsch.JSchException
            java.lang.String r6 = r6.toString()
            r7.<init>(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.jgss.GSSContextKrb5.create(java.lang.String, java.lang.String):void");
    }

    public boolean isEstablished() {
        return this.context.isEstablished();
    }

    public byte[] init(byte[] bArr, int i, int i2) throws JSchException {
        try {
            if (useSubjectCredsOnly == null) {
                setSystemProperty(pUseSubjectCredsOnly, "false");
            }
            byte[] initSecContext = this.context.initSecContext(bArr, 0, i2);
            if (useSubjectCredsOnly == null) {
                setSystemProperty(pUseSubjectCredsOnly, "true");
            }
            return initSecContext;
        } catch (GSSException e) {
            throw new JSchException(e.toString());
        } catch (SecurityException e2) {
            throw new JSchException(e2.toString());
        } catch (Throwable th) {
            if (useSubjectCredsOnly == null) {
                setSystemProperty(pUseSubjectCredsOnly, "true");
            }
            throw th;
        }
    }

    public byte[] getMIC(byte[] bArr, int i, int i2) {
        try {
            return this.context.getMIC(bArr, i, i2, new MessageProp(0, true));
        } catch (GSSException unused) {
            return null;
        }
    }

    public void dispose() {
        try {
            this.context.dispose();
        } catch (GSSException unused) {
        }
    }

    private static String getSystemProperty(String str) {
        try {
            return System.getProperty(str);
        } catch (Exception unused) {
            return null;
        }
    }

    private static void setSystemProperty(String str, String str2) {
        try {
            System.setProperty(str, str2);
        } catch (Exception unused) {
        }
    }
}
