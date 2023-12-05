package com.google.android.gms.common.internal;

import android.content.Intent;
import com.google.android.gms.internal.zzbff;

final class zzw extends zzt {
    private /* synthetic */ Intent val$intent;
    private /* synthetic */ int val$requestCode;
    private /* synthetic */ zzbff zzaHr;

    zzw(Intent intent, zzbff zzbff, int i) {
        this.val$intent = intent;
        this.zzaHr = zzbff;
        this.val$requestCode = i;
    }

    public final void zzrt() {
        Intent intent = this.val$intent;
        if (intent != null) {
            this.zzaHr.startActivityForResult(intent, this.val$requestCode);
        }
    }
}
