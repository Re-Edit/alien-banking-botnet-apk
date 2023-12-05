package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

final class zzbgg extends Handler {
    private /* synthetic */ zzbge zzaFk;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zzbgg(zzbge zzbge, Looper looper) {
        super(looper);
        this.zzaFk = zzbge;
    }

    public final void handleMessage(Message message) {
        switch (message.what) {
            case 0:
                PendingResult pendingResult = (PendingResult) message.obj;
                synchronized (this.zzaFk.zzaBY) {
                    if (pendingResult == null) {
                        this.zzaFk.zzaFd.zzv(new Status(13, "Transform returned null"));
                    } else if (pendingResult instanceof zzbft) {
                        this.zzaFk.zzaFd.zzv(((zzbft) pendingResult).getStatus());
                    } else {
                        this.zzaFk.zzaFd.zza(pendingResult);
                    }
                }
                return;
            case 1:
                RuntimeException runtimeException = (RuntimeException) message.obj;
                String valueOf = String.valueOf(runtimeException.getMessage());
                Log.e("TransformedResultImpl", valueOf.length() != 0 ? "Runtime exception on the transformation worker thread: ".concat(valueOf) : new String("Runtime exception on the transformation worker thread: "));
                throw runtimeException;
            default:
                int i = message.what;
                StringBuilder sb = new StringBuilder(70);
                sb.append("TransformationResultHandler received unknown message type: ");
                sb.append(i);
                Log.e("TransformedResultImpl", sb.toString());
                return;
        }
    }
}
