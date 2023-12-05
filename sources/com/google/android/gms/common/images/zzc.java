package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.google.android.gms.common.internal.zzbh;
import com.google.android.gms.internal.zzbgs;
import com.google.android.gms.internal.zzbgx;
import java.lang.ref.WeakReference;

public final class zzc extends zza {
    private WeakReference<ImageView> zzaGo;

    public zzc(ImageView imageView, int i) {
        super((Uri) null, i);
        com.google.android.gms.common.internal.zzc.zzr(imageView);
        this.zzaGo = new WeakReference<>(imageView);
    }

    public zzc(ImageView imageView, Uri uri) {
        super(uri, 0);
        com.google.android.gms.common.internal.zzc.zzr(imageView);
        this.zzaGo = new WeakReference<>(imageView);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzc)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ImageView imageView = (ImageView) this.zzaGo.get();
        ImageView imageView2 = (ImageView) ((zzc) obj).zzaGo.get();
        return (imageView2 == null || imageView == null || !zzbh.equal(imageView2, imageView)) ? false : true;
    }

    public final int hashCode() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public final void zza(Drawable drawable, boolean z, boolean z2, boolean z3) {
        ImageView imageView = (ImageView) this.zzaGo.get();
        if (imageView != null) {
            int i = 0;
            boolean z4 = !z2 && !z3;
            if (z4 && (imageView instanceof zzbgx)) {
                int zzqW = ((zzbgx) imageView).zzqW();
                if (this.zzaGj != 0 && zzqW == this.zzaGj) {
                    return;
                }
            }
            boolean zzc = zzc(z, z2);
            Uri uri = null;
            if (zzc) {
                Drawable drawable2 = imageView.getDrawable();
                if (drawable2 == null) {
                    drawable2 = null;
                } else if (drawable2 instanceof zzbgs) {
                    drawable2 = ((zzbgs) drawable2).zzqU();
                }
                drawable = new zzbgs(drawable2, drawable);
            }
            imageView.setImageDrawable(drawable);
            if (imageView instanceof zzbgx) {
                zzbgx zzbgx = (zzbgx) imageView;
                if (z3) {
                    uri = this.zzaGh.uri;
                }
                zzbgx.zzo(uri);
                if (z4) {
                    i = this.zzaGj;
                }
                zzbgx.zzax(i);
            }
            if (zzc) {
                ((zzbgs) drawable).startTransition(250);
            }
        }
    }
}
