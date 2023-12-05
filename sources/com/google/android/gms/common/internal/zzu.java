package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.Intent;

final class zzu extends zzt {
    private /* synthetic */ Activity val$activity;
    private /* synthetic */ Intent val$intent;
    private /* synthetic */ int val$requestCode;

    zzu(Intent intent, Activity activity, int i) {
        this.val$intent = intent;
        this.val$activity = activity;
        this.val$requestCode = i;
    }

    public final void zzrt() {
        Intent intent = this.val$intent;
        if (intent != null) {
            this.val$activity.startActivityForResult(intent, this.val$requestCode);
        }
    }
}
