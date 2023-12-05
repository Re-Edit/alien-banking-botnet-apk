package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class zzbib extends zza {
    public static final Parcelable.Creator<zzbib> CREATOR = new zzbie();
    final String className;
    private int versionCode;
    private ArrayList<zzbic> zzaIW;

    zzbib(int i, String str, ArrayList<zzbic> arrayList) {
        this.versionCode = i;
        this.className = str;
        this.zzaIW = arrayList;
    }

    zzbib(String str, Map<String, zzbhv<?, ?>> map) {
        ArrayList<zzbic> arrayList;
        this.versionCode = 1;
        this.className = str;
        if (map == null) {
            arrayList = null;
        } else {
            arrayList = new ArrayList<>();
            for (String next : map.keySet()) {
                arrayList.add(new zzbic(next, map.get(next)));
            }
        }
        this.zzaIW = arrayList;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.versionCode);
        zzd.zza(parcel, 2, this.className, false);
        zzd.zzc(parcel, 3, this.zzaIW, false);
        zzd.zzI(parcel, zze);
    }

    /* access modifiers changed from: package-private */
    public final HashMap<String, zzbhv<?, ?>> zzrR() {
        HashMap<String, zzbhv<?, ?>> hashMap = new HashMap<>();
        int size = this.zzaIW.size();
        for (int i = 0; i < size; i++) {
            zzbic zzbic = this.zzaIW.get(i);
            hashMap.put(zzbic.key, zzbic.zzaIX);
        }
        return hashMap;
    }
}
