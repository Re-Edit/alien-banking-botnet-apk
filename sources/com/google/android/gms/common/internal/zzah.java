package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Message;
import android.support.v4.os.EnvironmentCompat;
import android.util.Log;
import com.google.android.gms.common.stats.zza;
import java.util.HashMap;

final class zzah extends zzaf implements Handler.Callback {
    /* access modifiers changed from: private */
    public final Context mApplicationContext;
    /* access modifiers changed from: private */
    public final Handler mHandler;
    /* access modifiers changed from: private */
    public final HashMap<zzag, zzai> zzaHR = new HashMap<>();
    /* access modifiers changed from: private */
    public final zza zzaHS;
    private final long zzaHT;
    /* access modifiers changed from: private */
    public final long zzaHU;

    zzah(Context context) {
        this.mApplicationContext = context.getApplicationContext();
        this.mHandler = new Handler(context.getMainLooper(), this);
        this.zzaHS = zza.zzrT();
        this.zzaHT = 5000;
        this.zzaHU = 300000;
    }

    public final boolean handleMessage(Message message) {
        switch (message.what) {
            case 0:
                synchronized (this.zzaHR) {
                    zzag zzag = (zzag) message.obj;
                    zzai zzai = this.zzaHR.get(zzag);
                    if (zzai != null && zzai.zzrB()) {
                        if (zzai.isBound()) {
                            zzai.zzcC("GmsClientSupervisor");
                        }
                        this.zzaHR.remove(zzag);
                    }
                }
                return true;
            case 1:
                synchronized (this.zzaHR) {
                    zzag zzag2 = (zzag) message.obj;
                    zzai zzai2 = this.zzaHR.get(zzag2);
                    if (zzai2 != null && zzai2.getState() == 3) {
                        String valueOf = String.valueOf(zzag2);
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 47);
                        sb.append("Timeout waiting for ServiceConnection callback ");
                        sb.append(valueOf);
                        Log.wtf("GmsClientSupervisor", sb.toString(), new Exception());
                        ComponentName componentName = zzai2.getComponentName();
                        if (componentName == null) {
                            componentName = zzag2.getComponentName();
                        }
                        if (componentName == null) {
                            componentName = new ComponentName(zzag2.getPackage(), EnvironmentCompat.MEDIA_UNKNOWN);
                        }
                        zzai2.onServiceDisconnected(componentName);
                    }
                }
                return true;
            default:
                return false;
        }
    }

    /* access modifiers changed from: protected */
    public final boolean zza(zzag zzag, ServiceConnection serviceConnection, String str) {
        boolean isBound;
        zzbr.zzb(serviceConnection, (Object) "ServiceConnection must not be null");
        synchronized (this.zzaHR) {
            zzai zzai = this.zzaHR.get(zzag);
            if (zzai == null) {
                zzai = new zzai(this, zzag);
                zzai.zza(serviceConnection, str);
                zzai.zzcB(str);
                this.zzaHR.put(zzag, zzai);
            } else {
                this.mHandler.removeMessages(0, zzag);
                if (!zzai.zza(serviceConnection)) {
                    zzai.zza(serviceConnection, str);
                    switch (zzai.getState()) {
                        case 1:
                            serviceConnection.onServiceConnected(zzai.getComponentName(), zzai.getBinder());
                            break;
                        case 2:
                            zzai.zzcB(str);
                            break;
                    }
                } else {
                    String valueOf = String.valueOf(zzag);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 81);
                    sb.append("Trying to bind a GmsServiceConnection that was already connected before.  config=");
                    sb.append(valueOf);
                    throw new IllegalStateException(sb.toString());
                }
            }
            isBound = zzai.isBound();
        }
        return isBound;
    }

    /* access modifiers changed from: protected */
    public final void zzb(zzag zzag, ServiceConnection serviceConnection, String str) {
        zzbr.zzb(serviceConnection, (Object) "ServiceConnection must not be null");
        synchronized (this.zzaHR) {
            zzai zzai = this.zzaHR.get(zzag);
            if (zzai == null) {
                String valueOf = String.valueOf(zzag);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 50);
                sb.append("Nonexistent connection status for service config: ");
                sb.append(valueOf);
                throw new IllegalStateException(sb.toString());
            } else if (zzai.zza(serviceConnection)) {
                zzai.zzb(serviceConnection, str);
                if (zzai.zzrB()) {
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0, zzag), this.zzaHT);
                }
            } else {
                String valueOf2 = String.valueOf(zzag);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 76);
                sb2.append("Trying to unbind a GmsServiceConnection  that was not bound before.  config=");
                sb2.append(valueOf2);
                throw new IllegalStateException(sb2.toString());
            }
        }
    }
}
