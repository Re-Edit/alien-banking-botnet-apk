package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.support.annotation.BinderThread;
import com.google.android.gms.common.ConnectionResult;

abstract class zze extends zzi<Boolean> {
    private int statusCode;
    private Bundle zzaHf;
    private /* synthetic */ zzd zzaHg;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    @BinderThread
    protected zze(zzd zzd, int i, Bundle bundle) {
        super(zzd, true);
        this.zzaHg = zzd;
        this.statusCode = i;
        this.zzaHf = bundle;
    }

    /* access modifiers changed from: protected */
    public abstract void zzj(ConnectionResult connectionResult);

    /* access modifiers changed from: protected */
    public abstract boolean zzrh();

    /* JADX WARNING: type inference failed for: r4v11, types: [android.os.Parcelable] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ void zzs(java.lang.Object r4) {
        /*
            r3 = this;
            java.lang.Boolean r4 = (java.lang.Boolean) r4
            r0 = 1
            r1 = 0
            if (r4 != 0) goto L_0x000c
            com.google.android.gms.common.internal.zzd r4 = r3.zzaHg
            r4.zza((int) r0, null)
            return
        L_0x000c:
            int r4 = r3.statusCode
            if (r4 == 0) goto L_0x003e
            r2 = 10
            if (r4 == r2) goto L_0x0031
            com.google.android.gms.common.internal.zzd r4 = r3.zzaHg
            r4.zza((int) r0, null)
            android.os.Bundle r4 = r3.zzaHf
            if (r4 == 0) goto L_0x0026
            java.lang.String r0 = "pendingIntent"
            android.os.Parcelable r4 = r4.getParcelable(r0)
            r1 = r4
            android.app.PendingIntent r1 = (android.app.PendingIntent) r1
        L_0x0026:
            com.google.android.gms.common.ConnectionResult r4 = new com.google.android.gms.common.ConnectionResult
            int r0 = r3.statusCode
            r4.<init>(r0, r1)
            r3.zzj(r4)
            goto L_0x0053
        L_0x0031:
            com.google.android.gms.common.internal.zzd r4 = r3.zzaHg
            r4.zza((int) r0, null)
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r0 = "A fatal developer error has occurred. Check the logs for further information."
            r4.<init>(r0)
            throw r4
        L_0x003e:
            boolean r4 = r3.zzrh()
            if (r4 != 0) goto L_0x0053
            com.google.android.gms.common.internal.zzd r4 = r3.zzaHg
            r4.zza((int) r0, null)
            com.google.android.gms.common.ConnectionResult r4 = new com.google.android.gms.common.ConnectionResult
            r0 = 8
            r4.<init>(r0, r1)
            r3.zzj(r4)
        L_0x0053:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zze.zzs(java.lang.Object):void");
    }
}
