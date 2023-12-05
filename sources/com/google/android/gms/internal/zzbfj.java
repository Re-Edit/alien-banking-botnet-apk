package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.internal.zzbr;

final class zzbfj extends Handler {
    private /* synthetic */ zzbfi zzaEQ;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zzbfj(zzbfi zzbfi, Looper looper) {
        super(looper);
        this.zzaEQ = zzbfi;
    }

    public final void handleMessage(Message message) {
        boolean z = true;
        if (message.what != 1) {
            z = false;
        }
        zzbr.zzaf(z);
        this.zzaEQ.zzb((zzbfl) message.obj);
    }
}
