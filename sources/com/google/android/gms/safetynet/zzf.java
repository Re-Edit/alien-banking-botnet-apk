package com.google.android.gms.safetynet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzf extends zza {
    public static final Parcelable.Creator<zzf> CREATOR = new zzg();
    private final String zzakx;

    public zzf(String str) {
        this.zzakx = str;
    }

    public final String getTokenResult() {
        return this.zzakx;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, this.zzakx, false);
        zzd.zzI(parcel, zze);
    }
}
