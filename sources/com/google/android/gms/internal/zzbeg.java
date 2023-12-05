package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

final class zzbeg extends Handler {
    private /* synthetic */ zzbeb zzaDP;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbeg(zzbeb zzbeb, Looper looper) {
        super(looper);
        this.zzaDP = zzbeb;
    }

    public final void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                this.zzaDP.zzqb();
                return;
            case 2:
                this.zzaDP.resume();
                return;
            default:
                int i = message.what;
                StringBuilder sb = new StringBuilder(31);
                sb.append("Unknown message id: ");
                sb.append(i);
                Log.w("GoogleApiClientImpl", sb.toString());
                return;
        }
    }
}
