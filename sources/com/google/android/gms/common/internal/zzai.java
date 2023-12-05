package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.common.stats.zza;
import java.util.HashSet;
import java.util.Set;

final class zzai implements ServiceConnection {
    private int mState = 2;
    private ComponentName zzaHQ;
    private final Set<ServiceConnection> zzaHV = new HashSet();
    private boolean zzaHW;
    private final zzag zzaHX;
    private /* synthetic */ zzah zzaHY;
    private IBinder zzaHl;

    public zzai(zzah zzah, zzag zzag) {
        this.zzaHY = zzah;
        this.zzaHX = zzag;
    }

    public final IBinder getBinder() {
        return this.zzaHl;
    }

    public final ComponentName getComponentName() {
        return this.zzaHQ;
    }

    public final int getState() {
        return this.mState;
    }

    public final boolean isBound() {
        return this.zzaHW;
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this.zzaHY.zzaHR) {
            this.zzaHY.mHandler.removeMessages(1, this.zzaHX);
            this.zzaHl = iBinder;
            this.zzaHQ = componentName;
            for (ServiceConnection onServiceConnected : this.zzaHV) {
                onServiceConnected.onServiceConnected(componentName, iBinder);
            }
            this.mState = 1;
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        synchronized (this.zzaHY.zzaHR) {
            this.zzaHY.mHandler.removeMessages(1, this.zzaHX);
            this.zzaHl = null;
            this.zzaHQ = componentName;
            for (ServiceConnection onServiceDisconnected : this.zzaHV) {
                onServiceDisconnected.onServiceDisconnected(componentName);
            }
            this.mState = 2;
        }
    }

    public final void zza(ServiceConnection serviceConnection, String str) {
        zza unused = this.zzaHY.zzaHS;
        Context unused2 = this.zzaHY.mApplicationContext;
        this.zzaHX.zzrA();
        this.zzaHV.add(serviceConnection);
    }

    public final boolean zza(ServiceConnection serviceConnection) {
        return this.zzaHV.contains(serviceConnection);
    }

    public final void zzb(ServiceConnection serviceConnection, String str) {
        zza unused = this.zzaHY.zzaHS;
        Context unused2 = this.zzaHY.mApplicationContext;
        this.zzaHV.remove(serviceConnection);
    }

    public final void zzcB(String str) {
        this.mState = 3;
        zza unused = this.zzaHY.zzaHS;
        this.zzaHW = zza.zza(this.zzaHY.mApplicationContext, str, this.zzaHX.zzrA(), this, 129);
        if (this.zzaHW) {
            this.zzaHY.mHandler.sendMessageDelayed(this.zzaHY.mHandler.obtainMessage(1, this.zzaHX), this.zzaHY.zzaHU);
            return;
        }
        this.mState = 2;
        try {
            zza unused2 = this.zzaHY.zzaHS;
            this.zzaHY.mApplicationContext.unbindService(this);
        } catch (IllegalArgumentException unused3) {
        }
    }

    public final void zzcC(String str) {
        this.zzaHY.mHandler.removeMessages(1, this.zzaHX);
        zza unused = this.zzaHY.zzaHS;
        this.zzaHY.mApplicationContext.unbindService(this);
        this.zzaHW = false;
        this.mState = 2;
    }

    public final boolean zzrB() {
        return this.zzaHV.isEmpty();
    }
}
