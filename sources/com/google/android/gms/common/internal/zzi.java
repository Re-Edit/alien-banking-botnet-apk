package com.google.android.gms.common.internal;

import android.util.Log;

public abstract class zzi<TListener> {
    private TListener mListener;
    private /* synthetic */ zzd zzaHg;
    private boolean zzaHh = false;

    public zzi(zzd zzd, TListener tlistener) {
        this.zzaHg = zzd;
        this.mListener = tlistener;
    }

    public final void removeListener() {
        synchronized (this) {
            this.mListener = null;
        }
    }

    public final void unregister() {
        removeListener();
        synchronized (this.zzaHg.zzaGU) {
            this.zzaHg.zzaGU.remove(this);
        }
    }

    public final void zzri() {
        TListener tlistener;
        synchronized (this) {
            tlistener = this.mListener;
            if (this.zzaHh) {
                String valueOf = String.valueOf(this);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 47);
                sb.append("Callback proxy ");
                sb.append(valueOf);
                sb.append(" being reused. This is not safe.");
                Log.w("GmsClient", sb.toString());
            }
        }
        if (tlistener != null) {
            try {
                zzs(tlistener);
            } catch (RuntimeException e) {
                throw e;
            }
        }
        synchronized (this) {
            this.zzaHh = true;
        }
        unregister();
    }

    /* access modifiers changed from: protected */
    public abstract void zzs(TListener tlistener);
}
