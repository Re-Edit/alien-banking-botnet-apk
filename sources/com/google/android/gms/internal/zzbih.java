package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

public final class zzbih implements Executor {
    private final Handler mHandler;

    public zzbih(Looper looper) {
        this.mHandler = new Handler(looper);
    }

    public final void execute(@NonNull Runnable runnable) {
        this.mHandler.post(runnable);
    }
}
