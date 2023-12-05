package com.google.android.gms.common.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.internal.zzbgy;

public abstract class zza {
    final zzb zzaGh;
    private int zzaGi = 0;
    protected int zzaGj = 0;
    private boolean zzaGk = false;
    private boolean zzaGl = true;
    private boolean zzaGm = false;
    private boolean zzaGn = true;

    public zza(Uri uri, int i) {
        this.zzaGh = new zzb(uri);
        this.zzaGj = i;
    }

    /* access modifiers changed from: package-private */
    public final void zza(Context context, Bitmap bitmap, boolean z) {
        zzc.zzr(bitmap);
        zza(new BitmapDrawable(context.getResources(), bitmap), z, false, true);
    }

    /* access modifiers changed from: package-private */
    public final void zza(Context context, zzbgy zzbgy) {
        if (this.zzaGn) {
            zza((Drawable) null, false, true, false);
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(Context context, zzbgy zzbgy, boolean z) {
        int i = this.zzaGj;
        zza(i != 0 ? context.getResources().getDrawable(i) : null, z, false, false);
    }

    /* access modifiers changed from: protected */
    public abstract void zza(Drawable drawable, boolean z, boolean z2, boolean z3);

    /* access modifiers changed from: protected */
    public final boolean zzc(boolean z, boolean z2) {
        return this.zzaGl && !z2 && !z;
    }
}
