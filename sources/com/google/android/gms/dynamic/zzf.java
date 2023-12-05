package com.google.android.gms.dynamic;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

final class zzf implements View.OnClickListener {
    private /* synthetic */ Intent zzaSE;
    private /* synthetic */ Context zztI;

    zzf(Context context, Intent intent) {
        this.zztI = context;
        this.zzaSE = intent;
    }

    public final void onClick(View view) {
        try {
            this.zztI.startActivity(this.zzaSE);
        } catch (ActivityNotFoundException e) {
            Log.e("DeferredLifecycleHelper", "Failed to start resolution intent", e);
        }
    }
}
