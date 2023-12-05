package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbr;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class zzbia extends zza {
    public static final Parcelable.Creator<zzbia> CREATOR = new zzbid();
    private final HashMap<String, Map<String, zzbhv<?, ?>>> zzaIT;
    private final ArrayList<zzbib> zzaIU = null;
    private final String zzaIV;
    private int zzakw;

    zzbia(int i, ArrayList<zzbib> arrayList, String str) {
        this.zzakw = i;
        HashMap<String, Map<String, zzbhv<?, ?>>> hashMap = new HashMap<>();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            zzbib zzbib = arrayList.get(i2);
            hashMap.put(zzbib.className, zzbib.zzrR());
        }
        this.zzaIT = hashMap;
        this.zzaIV = (String) zzbr.zzu(str);
        zzrP();
    }

    private final void zzrP() {
        for (String str : this.zzaIT.keySet()) {
            Map map = this.zzaIT.get(str);
            for (String str2 : map.keySet()) {
                ((zzbhv) map.get(str2)).zza(this);
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        for (String next : this.zzaIT.keySet()) {
            sb.append(next);
            sb.append(":\n");
            Map map = this.zzaIT.get(next);
            for (String str : map.keySet()) {
                sb.append("  ");
                sb.append(str);
                sb.append(": ");
                sb.append(map.get(str));
            }
        }
        return sb.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        ArrayList arrayList = new ArrayList();
        for (String next : this.zzaIT.keySet()) {
            arrayList.add(new zzbib(next, this.zzaIT.get(next)));
        }
        zzd.zzc(parcel, 2, arrayList, false);
        zzd.zza(parcel, 3, this.zzaIV, false);
        zzd.zzI(parcel, zze);
    }

    public final Map<String, zzbhv<?, ?>> zzcJ(String str) {
        return this.zzaIT.get(str);
    }

    public final String zzrQ() {
        return this.zzaIV;
    }
}
