package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.zzc;
import java.util.Set;

public abstract class zzaa<T extends IInterface> extends zzd<T> implements Api.zze, zzae {
    private final zzq zzaCC;
    private final Account zzajd;
    private final Set<Scope> zzamg;

    protected zzaa(Context context, Looper looper, int i, zzq zzq, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, zzaf.zzaC(context), GoogleApiAvailability.getInstance(), i, zzq, (GoogleApiClient.ConnectionCallbacks) zzbr.zzu(connectionCallbacks), (GoogleApiClient.OnConnectionFailedListener) zzbr.zzu(onConnectionFailedListener));
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private zzaa(android.content.Context r11, android.os.Looper r12, com.google.android.gms.common.internal.zzaf r13, com.google.android.gms.common.GoogleApiAvailability r14, int r15, com.google.android.gms.common.internal.zzq r16, com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks r17, com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener r18) {
        /*
            r10 = this;
            r9 = r10
            r0 = r17
            r1 = r18
            r2 = 0
            if (r0 != 0) goto L_0x000a
            r6 = r2
            goto L_0x0010
        L_0x000a:
            com.google.android.gms.common.internal.zzab r3 = new com.google.android.gms.common.internal.zzab
            r3.<init>(r0)
            r6 = r3
        L_0x0010:
            if (r1 != 0) goto L_0x0014
            r7 = r2
            goto L_0x001a
        L_0x0014:
            com.google.android.gms.common.internal.zzac r0 = new com.google.android.gms.common.internal.zzac
            r0.<init>(r1)
            r7 = r0
        L_0x001a:
            java.lang.String r8 = r16.zzrp()
            r0 = r10
            r1 = r11
            r2 = r12
            r3 = r13
            r4 = r14
            r5 = r15
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            r0 = r16
            r9.zzaCC = r0
            android.accounts.Account r1 = r16.getAccount()
            r9.zzajd = r1
            java.util.Set r0 = r16.zzrm()
            java.util.Set r1 = r10.zzb(r0)
            java.util.Iterator r2 = r1.iterator()
        L_0x003d:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0058
            java.lang.Object r3 = r2.next()
            com.google.android.gms.common.api.Scope r3 = (com.google.android.gms.common.api.Scope) r3
            boolean r3 = r0.contains(r3)
            if (r3 == 0) goto L_0x0050
            goto L_0x003d
        L_0x0050:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Expanding scopes is not permitted, use implied scopes instead"
            r0.<init>(r1)
            throw r0
        L_0x0058:
            r9.zzamg = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzaa.<init>(android.content.Context, android.os.Looper, com.google.android.gms.common.internal.zzaf, com.google.android.gms.common.GoogleApiAvailability, int, com.google.android.gms.common.internal.zzq, com.google.android.gms.common.api.GoogleApiClient$ConnectionCallbacks, com.google.android.gms.common.api.GoogleApiClient$OnConnectionFailedListener):void");
    }

    public final Account getAccount() {
        return this.zzajd;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public Set<Scope> zzb(@NonNull Set<Scope> set) {
        return set;
    }

    public zzc[] zzrb() {
        return new zzc[0];
    }

    /* access modifiers changed from: protected */
    public final Set<Scope> zzrf() {
        return this.zzamg;
    }

    /* access modifiers changed from: protected */
    public final zzq zzrx() {
        return this.zzaCC;
    }
}
