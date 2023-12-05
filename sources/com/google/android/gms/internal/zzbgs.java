package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;

public final class zzbgs extends Drawable implements Drawable.Callback {
    private int mFrom;
    private boolean zzaGA;
    private boolean zzaGB;
    private int zzaGC;
    private boolean zzaGl;
    private int zzaGq;
    private int zzaGr;
    private int zzaGs;
    private int zzaGt;
    private int zzaGu;
    private boolean zzaGv;
    private zzbgw zzaGw;
    private Drawable zzaGx;
    private Drawable zzaGy;
    private boolean zzaGz;
    private long zzahb;

    public zzbgs(Drawable drawable, Drawable drawable2) {
        this((zzbgw) null);
        drawable = drawable == null ? zzbgu.zzaGD : drawable;
        this.zzaGx = drawable;
        drawable.setCallback(this);
        zzbgw zzbgw = this.zzaGw;
        zzbgw.zzaGF = drawable.getChangingConfigurations() | zzbgw.zzaGF;
        drawable2 = drawable2 == null ? zzbgu.zzaGD : drawable2;
        this.zzaGy = drawable2;
        drawable2.setCallback(this);
        zzbgw zzbgw2 = this.zzaGw;
        zzbgw2.zzaGF = drawable2.getChangingConfigurations() | zzbgw2.zzaGF;
    }

    zzbgs(zzbgw zzbgw) {
        this.zzaGq = 0;
        this.zzaGs = 255;
        this.zzaGu = 0;
        this.zzaGl = true;
        this.zzaGw = new zzbgw(zzbgw);
    }

    private final boolean canConstantState() {
        if (!this.zzaGz) {
            this.zzaGA = (this.zzaGx.getConstantState() == null || this.zzaGy.getConstantState() == null) ? false : true;
            this.zzaGz = true;
        }
        return this.zzaGA;
    }

    public final void draw(Canvas canvas) {
        boolean z = true;
        switch (this.zzaGq) {
            case 1:
                this.zzahb = SystemClock.uptimeMillis();
                this.zzaGq = 2;
                z = false;
                break;
            case 2:
                if (this.zzahb >= 0) {
                    float uptimeMillis = ((float) (SystemClock.uptimeMillis() - this.zzahb)) / ((float) this.zzaGt);
                    if (uptimeMillis < 1.0f) {
                        z = false;
                    }
                    if (z) {
                        this.zzaGq = 0;
                    }
                    this.zzaGu = (int) ((((float) this.zzaGr) * Math.min(uptimeMillis, 1.0f)) + 0.0f);
                    break;
                }
                break;
        }
        int i = this.zzaGu;
        boolean z2 = this.zzaGl;
        Drawable drawable = this.zzaGx;
        Drawable drawable2 = this.zzaGy;
        if (z) {
            if (!z2 || i == 0) {
                drawable.draw(canvas);
            }
            int i2 = this.zzaGs;
            if (i == i2) {
                drawable2.setAlpha(i2);
                drawable2.draw(canvas);
                return;
            }
            return;
        }
        if (z2) {
            drawable.setAlpha(this.zzaGs - i);
        }
        drawable.draw(canvas);
        if (z2) {
            drawable.setAlpha(this.zzaGs);
        }
        if (i > 0) {
            drawable2.setAlpha(i);
            drawable2.draw(canvas);
            drawable2.setAlpha(this.zzaGs);
        }
        invalidateSelf();
    }

    public final int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.zzaGw.mChangingConfigurations | this.zzaGw.zzaGF;
    }

    public final Drawable.ConstantState getConstantState() {
        if (!canConstantState()) {
            return null;
        }
        this.zzaGw.mChangingConfigurations = getChangingConfigurations();
        return this.zzaGw;
    }

    public final int getIntrinsicHeight() {
        return Math.max(this.zzaGx.getIntrinsicHeight(), this.zzaGy.getIntrinsicHeight());
    }

    public final int getIntrinsicWidth() {
        return Math.max(this.zzaGx.getIntrinsicWidth(), this.zzaGy.getIntrinsicWidth());
    }

    public final int getOpacity() {
        if (!this.zzaGB) {
            this.zzaGC = Drawable.resolveOpacity(this.zzaGx.getOpacity(), this.zzaGy.getOpacity());
            this.zzaGB = true;
        }
        return this.zzaGC;
    }

    public final void invalidateDrawable(Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public final Drawable mutate() {
        if (!this.zzaGv && super.mutate() == this) {
            if (canConstantState()) {
                this.zzaGx.mutate();
                this.zzaGy.mutate();
                this.zzaGv = true;
            } else {
                throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
            }
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public final void onBoundsChange(Rect rect) {
        this.zzaGx.setBounds(rect);
        this.zzaGy.setBounds(rect);
    }

    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    public final void setAlpha(int i) {
        if (this.zzaGu == this.zzaGs) {
            this.zzaGu = i;
        }
        this.zzaGs = i;
        invalidateSelf();
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        this.zzaGx.setColorFilter(colorFilter);
        this.zzaGy.setColorFilter(colorFilter);
    }

    public final void startTransition(int i) {
        this.mFrom = 0;
        this.zzaGr = this.zzaGs;
        this.zzaGu = 0;
        this.zzaGt = 250;
        this.zzaGq = 1;
        invalidateSelf();
    }

    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }

    public final Drawable zzqU() {
        return this.zzaGy;
    }
}
