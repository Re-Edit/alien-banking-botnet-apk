package com.google.android.gms.internal;

import java.io.IOException;

public final class aje extends ahz<aje> implements Cloneable {
    private int zzcwY = -1;
    private int zzcwZ = 0;

    public aje() {
        this.zzcuW = null;
        this.zzcvf = -1;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzMw */
    public aje clone() {
        try {
            return (aje) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aje)) {
            return false;
        }
        aje aje = (aje) obj;
        if (this.zzcwY == aje.zzcwY && this.zzcwZ == aje.zzcwZ) {
            return (this.zzcuW == null || this.zzcuW.isEmpty()) ? aje.zzcuW == null || aje.zzcuW.isEmpty() : this.zzcuW.equals(aje.zzcuW);
        }
        return false;
    }

    public final int hashCode() {
        return ((((((getClass().getName().hashCode() + 527) * 31) + this.zzcwY) * 31) + this.zzcwZ) * 31) + ((this.zzcuW == null || this.zzcuW.isEmpty()) ? 0 : this.zzcuW.hashCode());
    }

    public final /* synthetic */ ahz zzMd() throws CloneNotSupportedException {
        return (aje) clone();
    }

    public final /* synthetic */ aif zzMe() throws CloneNotSupportedException {
        return (aje) clone();
    }

    public final /* synthetic */ aif zza(ahw ahw) throws IOException {
        int i;
        while (true) {
            int zzLQ = ahw.zzLQ();
            if (zzLQ == 0) {
                return this;
            }
            if (zzLQ == 8) {
                i = ahw.getPosition();
                int zzLS = ahw.zzLS();
                switch (zzLS) {
                    case -1:
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                        this.zzcwY = zzLS;
                        continue;
                }
            } else if (zzLQ == 16) {
                i = ahw.getPosition();
                int zzLS2 = ahw.zzLS();
                if (zzLS2 != 100) {
                    switch (zzLS2) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                            break;
                    }
                }
                this.zzcwZ = zzLS2;
            } else if (!super.zza(ahw, zzLQ)) {
                return this;
            }
            ahw.zzco(i);
            zza(ahw, zzLQ);
        }
    }

    public final void zza(ahx ahx) throws IOException {
        int i = this.zzcwY;
        if (i != -1) {
            ahx.zzr(1, i);
        }
        int i2 = this.zzcwZ;
        if (i2 != 0) {
            ahx.zzr(2, i2);
        }
        super.zza(ahx);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        int i = this.zzcwY;
        if (i != -1) {
            zzn += ahx.zzs(1, i);
        }
        int i2 = this.zzcwZ;
        return i2 != 0 ? zzn + ahx.zzs(2, i2) : zzn;
    }
}
