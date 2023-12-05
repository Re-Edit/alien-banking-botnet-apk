package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public final class zzbcs<R extends Result> extends Handler {
    public zzbcs() {
        this(Looper.getMainLooper());
    }

    public zzbcs(Looper looper) {
        super(looper);
    }

    public final void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                Pair pair = (Pair) message.obj;
                ResultCallback resultCallback = (ResultCallback) pair.first;
                Result result = (Result) pair.second;
                try {
                    resultCallback.onResult(result);
                    return;
                } catch (RuntimeException e) {
                    zzbcq.zzc(result);
                    throw e;
                }
            case 2:
                ((zzbcq) message.obj).zzs(Status.zzaBr);
                return;
            default:
                int i = message.what;
                StringBuilder sb = new StringBuilder(45);
                sb.append("Don't know how to handle message: ");
                sb.append(i);
                Log.wtf("BasePendingResult", sb.toString(), new Exception());
                return;
        }
    }

    public final void zza(ResultCallback<? super R> resultCallback, R r) {
        sendMessage(obtainMessage(1, new Pair(resultCallback, r)));
    }
}
