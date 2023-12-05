package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzbho extends zza {
    public static final Parcelable.Creator<zzbho> CREATOR = new zzbhp();
    private final zzbhq zzaID;
    private int zzakw;

    zzbho(int i, zzbhq zzbhq) {
        this.zzakw = i;
        this.zzaID = zzbhq;
    }

    private zzbho(zzbhq zzbhq) {
        this.zzakw = 1;
        this.zzaID = zzbhq;
    }

    public static zzbho zza(zzbhw<?, ?> zzbhw) {
        if (zzbhw instanceof zzbhq) {
            return new zzbho((zzbhq) zzbhw);
        }
        throw new IllegalArgumentException("Unsupported safe parcelable field converter class.");
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        zzd.zza(parcel, 2, (Parcelable) this.zzaID, i, false);
        zzd.zzI(parcel, zze);
    }

    public final zzbhw<?, ?> zzrJ() {
        zzbhq zzbhq = this.zzaID;
        if (zzbhq != null) {
            return zzbhq;
        }
        throw new IllegalStateException("There was no converter wrapped in this ConverterWrapper.");
    }
}
