package com.google.android.gms.internal;

import java.io.IOException;

public final class ajd extends ahz<ajd> implements Cloneable {
    private static volatile ajd[] zzcwX;
    private String key = "";
    private String value = "";

    public ajd() {
        this.zzcuW = null;
        this.zzcvf = -1;
    }

    public static ajd[] zzMu() {
        if (zzcwX == null) {
            synchronized (aid.zzcve) {
                if (zzcwX == null) {
                    zzcwX = new ajd[0];
                }
            }
        }
        return zzcwX;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzMv */
    public ajd clone() {
        try {
            return (ajd) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ajd)) {
            return false;
        }
        ajd ajd = (ajd) obj;
        String str = this.key;
        if (str == null) {
            if (ajd.key != null) {
                return false;
            }
        } else if (!str.equals(ajd.key)) {
            return false;
        }
        String str2 = this.value;
        if (str2 == null) {
            if (ajd.value != null) {
                return false;
            }
        } else if (!str2.equals(ajd.value)) {
            return false;
        }
        return (this.zzcuW == null || this.zzcuW.isEmpty()) ? ajd.zzcuW == null || ajd.zzcuW.isEmpty() : this.zzcuW.equals(ajd.zzcuW);
    }

    public final int hashCode() {
        int hashCode = (getClass().getName().hashCode() + 527) * 31;
        String str = this.key;
        int i = 0;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.value;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        if (this.zzcuW != null && !this.zzcuW.isEmpty()) {
            i = this.zzcuW.hashCode();
        }
        return hashCode3 + i;
    }

    public final /* synthetic */ ahz zzMd() throws CloneNotSupportedException {
        return (ajd) clone();
    }

    public final /* synthetic */ aif zzMe() throws CloneNotSupportedException {
        return (ajd) clone();
    }

    public final /* synthetic */ aif zza(ahw ahw) throws IOException {
        while (true) {
            int zzLQ = ahw.zzLQ();
            if (zzLQ == 0) {
                return this;
            }
            if (zzLQ == 10) {
                this.key = ahw.readString();
            } else if (zzLQ == 18) {
                this.value = ahw.readString();
            } else if (!super.zza(ahw, zzLQ)) {
                return this;
            }
        }
    }

    public final void zza(ahx ahx) throws IOException {
        String str = this.key;
        if (str != null && !str.equals("")) {
            ahx.zzl(1, this.key);
        }
        String str2 = this.value;
        if (str2 != null && !str2.equals("")) {
            ahx.zzl(2, this.value);
        }
        super.zza(ahx);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        String str = this.key;
        if (str != null && !str.equals("")) {
            zzn += ahx.zzm(1, this.key);
        }
        String str2 = this.value;
        return (str2 == null || str2.equals("")) ? zzn : zzn + ahx.zzm(2, this.value);
    }
}
