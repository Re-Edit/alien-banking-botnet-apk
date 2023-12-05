package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

@Deprecated
public final class zzcc extends zza {
    public static final Parcelable.Creator<zzcc> CREATOR = new zzcd();
    private int zzakw;

    zzcc(int i) {
        this.zzakw = i;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        zzd.zzI(parcel, zze);
    }
}
