package com.google.android.gms.common;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzas;
import com.google.android.gms.common.internal.zzat;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.util.zzn;
import com.google.android.gms.dynamic.IObjectWrapper;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

abstract class zzg extends zzat {
    private int zzaAi;

    protected zzg(byte[] bArr) {
        if (bArr.length != 25) {
            int length = bArr.length;
            boolean z = false;
            String valueOf = String.valueOf(zzn.zza(bArr, 0, bArr.length, false));
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 51);
            sb.append("Cert hash data has incorrect length (");
            sb.append(length);
            sb.append("):\n");
            sb.append(valueOf);
            Log.wtf("GoogleCertificates", sb.toString(), new Exception());
            bArr = Arrays.copyOfRange(bArr, 0, 25);
            z = bArr.length == 25 ? true : z;
            int length2 = bArr.length;
            StringBuilder sb2 = new StringBuilder(55);
            sb2.append("cert hash data has incorrect length. length=");
            sb2.append(length2);
            zzbr.zzb(z, (Object) sb2.toString());
        }
        this.zzaAi = Arrays.hashCode(bArr);
    }

    protected static byte[] zzcs(String str) {
        try {
            return str.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public boolean equals(Object obj) {
        IObjectWrapper zzoW;
        if (obj != null && (obj instanceof zzas)) {
            try {
                zzas zzas = (zzas) obj;
                if (zzas.zzoX() != hashCode() || (zzoW = zzas.zzoW()) == null) {
                    return false;
                }
                return Arrays.equals(getBytes(), (byte[]) com.google.android.gms.dynamic.zzn.zzE(zzoW));
            } catch (RemoteException e) {
                Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public abstract byte[] getBytes();

    public int hashCode() {
        return this.zzaAi;
    }

    public final IObjectWrapper zzoW() {
        return com.google.android.gms.dynamic.zzn.zzw(getBytes());
    }

    public final int zzoX() {
        return hashCode();
    }
}
