package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbig implements Parcelable.Creator<zzbif> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        Parcel parcel2 = null;
        int i = 0;
        zzbia zzbia = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 2:
                    parcel2 = zzb.zzD(parcel, readInt);
                    break;
                case 3:
                    zzbia = (zzbia) zzb.zza(parcel, readInt, zzbia.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbif(i, parcel2, zzbia);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbif[i];
    }
}
