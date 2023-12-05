package com.google.android.gms.internal;

import android.database.ContentObserver;
import android.os.Handler;

final class iv extends ContentObserver {
    iv(Handler handler) {
        super((Handler) null);
    }

    public final void onChange(boolean z) {
        iu.zzbUf.set(true);
    }
}
