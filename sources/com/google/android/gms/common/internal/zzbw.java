package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzbw extends zza {
    public static final Parcelable.Creator<zzbw> CREATOR = new zzbx();
    private final int zzaIu;
    private final int zzaIv;
    @Deprecated
    private final Scope[] zzaIw;
    private int zzakw;

    zzbw(int i, int i2, int i3, Scope[] scopeArr) {
        this.zzakw = i;
        this.zzaIu = i2;
        this.zzaIv = i3;
        this.zzaIw = scopeArr;
    }

    public zzbw(int i, int i2, Scope[] scopeArr) {
        this(1, i, i2, (Scope[]) null);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        zzd.zzc(parcel, 2, this.zzaIu);
        zzd.zzc(parcel, 3, this.zzaIv);
        zzd.zza(parcel, 4, (T[]) this.zzaIw, i, false);
        zzd.zzI(parcel, zze);
    }
}
