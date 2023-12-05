package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;

public final class zzl implements ServiceConnection {
    private /* synthetic */ zzd zzaHg;
    private final int zzaHj;

    public zzl(zzd zzd, int i) {
        this.zzaHg = zzd;
        this.zzaHj = i;
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        zzay zzay;
        if (iBinder == null) {
            this.zzaHg.zzaz(16);
            return;
        }
        synchronized (this.zzaHg.zzaGQ) {
            zzd zzd = this.zzaHg;
            if (iBinder == null) {
                zzay = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                zzay = (queryLocalInterface == null || !(queryLocalInterface instanceof zzay)) ? new zzba(iBinder) : (zzay) queryLocalInterface;
            }
            zzay unused = zzd.zzaGR = zzay;
        }
        this.zzaHg.zza(0, (Bundle) null, this.zzaHj);
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        synchronized (this.zzaHg.zzaGQ) {
            zzay unused = this.zzaHg.zzaGR = null;
        }
        this.zzaHg.mHandler.sendMessage(this.zzaHg.mHandler.obtainMessage(6, this.zzaHj, 1));
    }
}
