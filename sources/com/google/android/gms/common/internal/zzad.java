package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzad implements Handler.Callback {
    private final Handler mHandler;
    private final Object mLock = new Object();
    private final zzae zzaHG;
    private final ArrayList<GoogleApiClient.ConnectionCallbacks> zzaHH = new ArrayList<>();
    private ArrayList<GoogleApiClient.ConnectionCallbacks> zzaHI = new ArrayList<>();
    private final ArrayList<GoogleApiClient.OnConnectionFailedListener> zzaHJ = new ArrayList<>();
    private volatile boolean zzaHK = false;
    private final AtomicInteger zzaHL = new AtomicInteger(0);
    private boolean zzaHM = false;

    public zzad(Looper looper, zzae zzae) {
        this.zzaHG = zzae;
        this.mHandler = new Handler(looper, this);
    }

    public final boolean handleMessage(Message message) {
        if (message.what == 1) {
            GoogleApiClient.ConnectionCallbacks connectionCallbacks = (GoogleApiClient.ConnectionCallbacks) message.obj;
            synchronized (this.mLock) {
                if (this.zzaHK && this.zzaHG.isConnected() && this.zzaHH.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnected(this.zzaHG.zzoA());
                }
            }
            return true;
        }
        int i = message.what;
        StringBuilder sb = new StringBuilder(45);
        sb.append("Don't know how to handle message: ");
        sb.append(i);
        Log.wtf("GmsClientEvents", sb.toString(), new Exception());
        return false;
    }

    public final boolean isConnectionCallbacksRegistered(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        boolean contains;
        zzbr.zzu(connectionCallbacks);
        synchronized (this.mLock) {
            contains = this.zzaHH.contains(connectionCallbacks);
        }
        return contains;
    }

    public final boolean isConnectionFailedListenerRegistered(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        boolean contains;
        zzbr.zzu(onConnectionFailedListener);
        synchronized (this.mLock) {
            contains = this.zzaHJ.contains(onConnectionFailedListener);
        }
        return contains;
    }

    public final void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        zzbr.zzu(connectionCallbacks);
        synchronized (this.mLock) {
            if (this.zzaHH.contains(connectionCallbacks)) {
                String valueOf = String.valueOf(connectionCallbacks);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 62);
                sb.append("registerConnectionCallbacks(): listener ");
                sb.append(valueOf);
                sb.append(" is already registered");
                Log.w("GmsClientEvents", sb.toString());
            } else {
                this.zzaHH.add(connectionCallbacks);
            }
        }
        if (this.zzaHG.isConnected()) {
            Handler handler = this.mHandler;
            handler.sendMessage(handler.obtainMessage(1, connectionCallbacks));
        }
    }

    public final void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        zzbr.zzu(onConnectionFailedListener);
        synchronized (this.mLock) {
            if (this.zzaHJ.contains(onConnectionFailedListener)) {
                String valueOf = String.valueOf(onConnectionFailedListener);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 67);
                sb.append("registerConnectionFailedListener(): listener ");
                sb.append(valueOf);
                sb.append(" is already registered");
                Log.w("GmsClientEvents", sb.toString());
            } else {
                this.zzaHJ.add(onConnectionFailedListener);
            }
        }
    }

    public final void unregisterConnectionCallbacks(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        zzbr.zzu(connectionCallbacks);
        synchronized (this.mLock) {
            if (!this.zzaHH.remove(connectionCallbacks)) {
                String valueOf = String.valueOf(connectionCallbacks);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 52);
                sb.append("unregisterConnectionCallbacks(): listener ");
                sb.append(valueOf);
                sb.append(" not found");
                Log.w("GmsClientEvents", sb.toString());
            } else if (this.zzaHM) {
                this.zzaHI.add(connectionCallbacks);
            }
        }
    }

    public final void unregisterConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        zzbr.zzu(onConnectionFailedListener);
        synchronized (this.mLock) {
            if (!this.zzaHJ.remove(onConnectionFailedListener)) {
                String valueOf = String.valueOf(onConnectionFailedListener);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 57);
                sb.append("unregisterConnectionFailedListener(): listener ");
                sb.append(valueOf);
                sb.append(" not found");
                Log.w("GmsClientEvents", sb.toString());
            }
        }
    }

    public final void zzaA(int i) {
        zzbr.zza(Looper.myLooper() == this.mHandler.getLooper(), (Object) "onUnintentionalDisconnection must only be called on the Handler thread");
        this.mHandler.removeMessages(1);
        synchronized (this.mLock) {
            this.zzaHM = true;
            ArrayList arrayList = new ArrayList(this.zzaHH);
            int i2 = this.zzaHL.get();
            ArrayList arrayList2 = arrayList;
            int size = arrayList2.size();
            int i3 = 0;
            while (i3 < size) {
                Object obj = arrayList2.get(i3);
                i3++;
                GoogleApiClient.ConnectionCallbacks connectionCallbacks = (GoogleApiClient.ConnectionCallbacks) obj;
                if (!this.zzaHK || this.zzaHL.get() != i2) {
                    break;
                } else if (this.zzaHH.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnectionSuspended(i);
                }
            }
            this.zzaHI.clear();
            this.zzaHM = false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0055, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzk(com.google.android.gms.common.ConnectionResult r8) {
        /*
            r7 = this;
            android.os.Looper r0 = android.os.Looper.myLooper()
            android.os.Handler r1 = r7.mHandler
            android.os.Looper r1 = r1.getLooper()
            r2 = 0
            r3 = 1
            if (r0 != r1) goto L_0x0010
            r0 = 1
            goto L_0x0011
        L_0x0010:
            r0 = 0
        L_0x0011:
            java.lang.String r1 = "onConnectionFailure must only be called on the Handler thread"
            com.google.android.gms.common.internal.zzbr.zza((boolean) r0, (java.lang.Object) r1)
            android.os.Handler r0 = r7.mHandler
            r0.removeMessages(r3)
            java.lang.Object r0 = r7.mLock
            monitor-enter(r0)
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x0058 }
            java.util.ArrayList<com.google.android.gms.common.api.GoogleApiClient$OnConnectionFailedListener> r3 = r7.zzaHJ     // Catch:{ all -> 0x0058 }
            r1.<init>(r3)     // Catch:{ all -> 0x0058 }
            java.util.concurrent.atomic.AtomicInteger r3 = r7.zzaHL     // Catch:{ all -> 0x0058 }
            int r3 = r3.get()     // Catch:{ all -> 0x0058 }
            java.util.ArrayList r1 = (java.util.ArrayList) r1     // Catch:{ all -> 0x0058 }
            int r4 = r1.size()     // Catch:{ all -> 0x0058 }
        L_0x0031:
            if (r2 >= r4) goto L_0x0056
            java.lang.Object r5 = r1.get(r2)     // Catch:{ all -> 0x0058 }
            int r2 = r2 + 1
            com.google.android.gms.common.api.GoogleApiClient$OnConnectionFailedListener r5 = (com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener) r5     // Catch:{ all -> 0x0058 }
            boolean r6 = r7.zzaHK     // Catch:{ all -> 0x0058 }
            if (r6 == 0) goto L_0x0054
            java.util.concurrent.atomic.AtomicInteger r6 = r7.zzaHL     // Catch:{ all -> 0x0058 }
            int r6 = r6.get()     // Catch:{ all -> 0x0058 }
            if (r6 == r3) goto L_0x0048
            goto L_0x0054
        L_0x0048:
            java.util.ArrayList<com.google.android.gms.common.api.GoogleApiClient$OnConnectionFailedListener> r6 = r7.zzaHJ     // Catch:{ all -> 0x0058 }
            boolean r6 = r6.contains(r5)     // Catch:{ all -> 0x0058 }
            if (r6 == 0) goto L_0x0031
            r5.onConnectionFailed(r8)     // Catch:{ all -> 0x0058 }
            goto L_0x0031
        L_0x0054:
            monitor-exit(r0)     // Catch:{ all -> 0x0058 }
            return
        L_0x0056:
            monitor-exit(r0)     // Catch:{ all -> 0x0058 }
            return
        L_0x0058:
            r8 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0058 }
            throw r8
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzad.zzk(com.google.android.gms.common.ConnectionResult):void");
    }

    public final void zzn(Bundle bundle) {
        boolean z = true;
        zzbr.zza(Looper.myLooper() == this.mHandler.getLooper(), (Object) "onConnectionSuccess must only be called on the Handler thread");
        synchronized (this.mLock) {
            zzbr.zzae(!this.zzaHM);
            this.mHandler.removeMessages(1);
            this.zzaHM = true;
            if (this.zzaHI.size() != 0) {
                z = false;
            }
            zzbr.zzae(z);
            ArrayList arrayList = new ArrayList(this.zzaHH);
            int i = this.zzaHL.get();
            ArrayList arrayList2 = arrayList;
            int size = arrayList2.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList2.get(i2);
                i2++;
                GoogleApiClient.ConnectionCallbacks connectionCallbacks = (GoogleApiClient.ConnectionCallbacks) obj;
                if (!this.zzaHK || !this.zzaHG.isConnected() || this.zzaHL.get() != i) {
                    break;
                } else if (!this.zzaHI.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnected(bundle);
                }
            }
            this.zzaHI.clear();
            this.zzaHM = false;
        }
    }

    public final void zzry() {
        this.zzaHK = false;
        this.zzaHL.incrementAndGet();
    }

    public final void zzrz() {
        this.zzaHK = true;
    }
}
