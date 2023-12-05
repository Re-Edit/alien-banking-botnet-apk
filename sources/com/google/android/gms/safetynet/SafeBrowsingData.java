package com.google.android.gms.safetynet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public class SafeBrowsingData extends zza {
    public static final Parcelable.Creator<SafeBrowsingData> CREATOR = new zzh();
    private String zzbBM;
    private DataHolder zzbBN;

    public SafeBrowsingData(String str) {
        this(str, (DataHolder) null);
    }

    public SafeBrowsingData(String str, DataHolder dataHolder) {
        this.zzbBM = str;
        this.zzbBN = dataHolder;
    }

    public DataHolder getBlacklistsDataHolder() {
        return this.zzbBN;
    }

    public String getMetadata() {
        return this.zzbBM;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, getMetadata(), false);
        zzd.zza(parcel, 3, (Parcelable) getBlacklistsDataHolder(), i, false);
        zzd.zzI(parcel, zze);
    }
}
