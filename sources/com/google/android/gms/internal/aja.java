package com.google.android.gms.internal;

import java.io.IOException;

public final class aja extends ahz<aja> implements Cloneable {
    private String version = "";
    private int zzbpf = 0;
    private String zzcwz = "";

    public aja() {
        this.zzcuW = null;
        this.zzcvf = -1;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzMr */
    public aja clone() {
        try {
            return (aja) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aja)) {
            return false;
        }
        aja aja = (aja) obj;
        if (this.zzbpf != aja.zzbpf) {
            return false;
        }
        String str = this.zzcwz;
        if (str == null) {
            if (aja.zzcwz != null) {
                return false;
            }
        } else if (!str.equals(aja.zzcwz)) {
            return false;
        }
        String str2 = this.version;
        if (str2 == null) {
            if (aja.version != null) {
                return false;
            }
        } else if (!str2.equals(aja.version)) {
            return false;
        }
        return (this.zzcuW == null || this.zzcuW.isEmpty()) ? aja.zzcuW == null || aja.zzcuW.isEmpty() : this.zzcuW.equals(aja.zzcuW);
    }

    public final int hashCode() {
        int hashCode = (((getClass().getName().hashCode() + 527) * 31) + this.zzbpf) * 31;
        String str = this.zzcwz;
        int i = 0;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.version;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        if (this.zzcuW != null && !this.zzcuW.isEmpty()) {
            i = this.zzcuW.hashCode();
        }
        return hashCode3 + i;
    }

    public final /* synthetic */ ahz zzMd() throws CloneNotSupportedException {
        return (aja) clone();
    }

    public final /* synthetic */ aif zzMe() throws CloneNotSupportedException {
        return (aja) clone();
    }

    public final /* synthetic */ aif zza(ahw ahw) throws IOException {
        while (true) {
            int zzLQ = ahw.zzLQ();
            if (zzLQ == 0) {
                return this;
            }
            if (zzLQ == 8) {
                this.zzbpf = ahw.zzLS();
            } else if (zzLQ == 18) {
                this.zzcwz = ahw.readString();
            } else if (zzLQ == 26) {
                this.version = ahw.readString();
            } else if (!super.zza(ahw, zzLQ)) {
                return this;
            }
        }
    }

    public final void zza(ahx ahx) throws IOException {
        int i = this.zzbpf;
        if (i != 0) {
            ahx.zzr(1, i);
        }
        String str = this.zzcwz;
        if (str != null && !str.equals("")) {
            ahx.zzl(2, this.zzcwz);
        }
        String str2 = this.version;
        if (str2 != null && !str2.equals("")) {
            ahx.zzl(3, this.version);
        }
        super.zza(ahx);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        int i = this.zzbpf;
        if (i != 0) {
            zzn += ahx.zzs(1, i);
        }
        String str = this.zzcwz;
        if (str != null && !str.equals("")) {
            zzn += ahx.zzm(2, this.zzcwz);
        }
        String str2 = this.version;
        return (str2 == null || str2.equals("")) ? zzn : zzn + ahx.zzm(3, this.version);
    }
}
