package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import java.util.concurrent.atomic.AtomicReference;

public abstract class zzbcm extends zzbfe implements DialogInterface.OnCancelListener {
    protected volatile boolean mStarted;
    protected final AtomicReference<zzbcn> zzaBP;
    private final Handler zzaBQ;
    protected final GoogleApiAvailability zzaBf;

    protected zzbcm(zzbff zzbff) {
        this(zzbff, GoogleApiAvailability.getInstance());
    }

    private zzbcm(zzbff zzbff, GoogleApiAvailability googleApiAvailability) {
        super(zzbff);
        this.zzaBP = new AtomicReference<>((Object) null);
        this.zzaBQ = new Handler(Looper.getMainLooper());
        this.zzaBf = googleApiAvailability;
    }

    private static int zza(@Nullable zzbcn zzbcn) {
        if (zzbcn == null) {
            return -1;
        }
        return zzbcn.zzpw();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onActivityResult(int r4, int r5, android.content.Intent r6) {
        /*
            r3 = this;
            java.util.concurrent.atomic.AtomicReference<com.google.android.gms.internal.zzbcn> r0 = r3.zzaBP
            java.lang.Object r0 = r0.get()
            com.google.android.gms.internal.zzbcn r0 = (com.google.android.gms.internal.zzbcn) r0
            r1 = 1
            r2 = 0
            switch(r4) {
                case 1: goto L_0x002e;
                case 2: goto L_0x000e;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x0053
        L_0x000e:
            com.google.android.gms.common.GoogleApiAvailability r4 = r3.zzaBf
            android.app.Activity r5 = r3.getActivity()
            int r4 = r4.isGooglePlayServicesAvailable(r5)
            if (r4 != 0) goto L_0x001b
            goto L_0x001c
        L_0x001b:
            r1 = 0
        L_0x001c:
            if (r0 != 0) goto L_0x001f
            return
        L_0x001f:
            com.google.android.gms.common.ConnectionResult r5 = r0.zzpx()
            int r5 = r5.getErrorCode()
            r6 = 18
            if (r5 != r6) goto L_0x0054
            if (r4 != r6) goto L_0x0054
            return
        L_0x002e:
            r4 = -1
            if (r5 != r4) goto L_0x0032
            goto L_0x0054
        L_0x0032:
            if (r5 != 0) goto L_0x0053
            r4 = 13
            if (r6 == 0) goto L_0x003e
            java.lang.String r5 = "<<ResolutionFailureErrorDetail>>"
            int r4 = r6.getIntExtra(r5, r4)
        L_0x003e:
            com.google.android.gms.internal.zzbcn r5 = new com.google.android.gms.internal.zzbcn
            com.google.android.gms.common.ConnectionResult r6 = new com.google.android.gms.common.ConnectionResult
            r1 = 0
            r6.<init>(r4, r1)
            int r4 = zza(r0)
            r5.<init>(r6, r4)
            java.util.concurrent.atomic.AtomicReference<com.google.android.gms.internal.zzbcn> r4 = r3.zzaBP
            r4.set(r5)
            r0 = r5
        L_0x0053:
            r1 = 0
        L_0x0054:
            if (r1 == 0) goto L_0x005a
            r3.zzpv()
            return
        L_0x005a:
            if (r0 == 0) goto L_0x0067
            com.google.android.gms.common.ConnectionResult r4 = r0.zzpx()
            int r5 = r0.zzpw()
            r3.zza(r4, r5)
        L_0x0067:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbcm.onActivityResult(int, int, android.content.Intent):void");
    }

    public void onCancel(DialogInterface dialogInterface) {
        zza(new ConnectionResult(13, (PendingIntent) null), zza(this.zzaBP.get()));
        zzpv();
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.zzaBP.set(bundle.getBoolean("resolving_error", false) ? new zzbcn(new ConnectionResult(bundle.getInt("failed_status"), (PendingIntent) bundle.getParcelable("failed_resolution")), bundle.getInt("failed_client_id", -1)) : null);
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        zzbcn zzbcn = this.zzaBP.get();
        if (zzbcn != null) {
            bundle.putBoolean("resolving_error", true);
            bundle.putInt("failed_client_id", zzbcn.zzpw());
            bundle.putInt("failed_status", zzbcn.zzpx().getErrorCode());
            bundle.putParcelable("failed_resolution", zzbcn.zzpx().getResolution());
        }
    }

    public void onStart() {
        super.onStart();
        this.mStarted = true;
    }

    public void onStop() {
        super.onStop();
        this.mStarted = false;
    }

    /* access modifiers changed from: protected */
    public abstract void zza(ConnectionResult connectionResult, int i);

    public final void zzb(ConnectionResult connectionResult, int i) {
        zzbcn zzbcn = new zzbcn(connectionResult, i);
        if (this.zzaBP.compareAndSet((Object) null, zzbcn)) {
            this.zzaBQ.post(new zzbco(this, zzbcn));
        }
    }

    /* access modifiers changed from: protected */
    public abstract void zzpq();

    /* access modifiers changed from: protected */
    public final void zzpv() {
        this.zzaBP.set((Object) null);
        zzpq();
    }
}
