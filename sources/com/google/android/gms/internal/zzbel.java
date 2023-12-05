package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

final class zzbel extends Handler {
    private /* synthetic */ zzbej zzaEc;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbel(zzbej zzbej, Looper looper) {
        super(looper);
        this.zzaEc = zzbej;
    }

    public final void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                ((zzbek) message.obj).zzc(this.zzaEc);
                return;
            case 2:
                throw ((RuntimeException) message.obj);
            default:
                int i = message.what;
                StringBuilder sb = new StringBuilder(31);
                sb.append("Unknown message id: ");
                sb.append(i);
                Log.w("GACStateManager", sb.toString());
                return;
        }
    }
}
