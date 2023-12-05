package com.google.android.gms.internal;

import android.graphics.drawable.Drawable;

final class zzbgw extends Drawable.ConstantState {
    int mChangingConfigurations;
    int zzaGF;

    zzbgw(zzbgw zzbgw) {
        if (zzbgw != null) {
            this.mChangingConfigurations = zzbgw.mChangingConfigurations;
            this.zzaGF = zzbgw.zzaGF;
        }
    }

    public final int getChangingConfigurations() {
        return this.mChangingConfigurations;
    }

    public final Drawable newDrawable() {
        return new zzbgs(this);
    }
}
