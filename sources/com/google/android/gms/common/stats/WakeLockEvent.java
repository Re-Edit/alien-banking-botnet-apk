package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.List;

public final class WakeLockEvent extends StatsEvent {
    public static final Parcelable.Creator<WakeLockEvent> CREATOR = new zzd();
    private final long mTimeout;
    private final float zzaJA;
    private long zzaJB;
    private final long zzaJp;
    private int zzaJq;
    private final String zzaJr;
    private final String zzaJs;
    private final String zzaJt;
    private final int zzaJu;
    private final List<String> zzaJv;
    private final String zzaJw;
    private final long zzaJx;
    private int zzaJy;
    private final String zzaJz;
    private int zzakw;

    WakeLockEvent(int i, long j, int i2, String str, int i3, List<String> list, String str2, long j2, int i4, String str3, String str4, float f, long j3, String str5) {
        this.zzakw = i;
        this.zzaJp = j;
        this.zzaJq = i2;
        this.zzaJr = str;
        this.zzaJs = str3;
        this.zzaJt = str5;
        this.zzaJu = i3;
        this.zzaJB = -1;
        this.zzaJv = list;
        this.zzaJw = str2;
        this.zzaJx = j2;
        this.zzaJy = i4;
        this.zzaJz = str4;
        this.zzaJA = f;
        this.mTimeout = j3;
    }

    public WakeLockEvent(long j, int i, String str, int i2, List<String> list, String str2, long j2, int i3, String str3, String str4, float f, long j3, String str5) {
        this(2, j, i, str, i2, list, str2, j2, i3, str3, str4, f, j3, str5);
    }

    public final int getEventType() {
        return this.zzaJq;
    }

    public final long getTimeMillis() {
        return this.zzaJp;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        zzd.zza(parcel, 2, this.zzaJp);
        zzd.zza(parcel, 4, this.zzaJr, false);
        zzd.zzc(parcel, 5, this.zzaJu);
        zzd.zzb(parcel, 6, this.zzaJv, false);
        zzd.zza(parcel, 8, this.zzaJx);
        zzd.zza(parcel, 10, this.zzaJs, false);
        zzd.zzc(parcel, 11, this.zzaJq);
        zzd.zza(parcel, 12, this.zzaJw, false);
        zzd.zza(parcel, 13, this.zzaJz, false);
        zzd.zzc(parcel, 14, this.zzaJy);
        zzd.zza(parcel, 15, this.zzaJA);
        zzd.zza(parcel, 16, this.mTimeout);
        zzd.zza(parcel, 17, this.zzaJt, false);
        zzd.zzI(parcel, zze);
    }

    public final long zzrU() {
        return this.zzaJB;
    }

    public final String zzrV() {
        String valueOf = String.valueOf("\t");
        String valueOf2 = String.valueOf(this.zzaJr);
        String valueOf3 = String.valueOf("\t");
        int i = this.zzaJu;
        String valueOf4 = String.valueOf("\t");
        List<String> list = this.zzaJv;
        String join = list == null ? "" : TextUtils.join(",", list);
        String valueOf5 = String.valueOf("\t");
        int i2 = this.zzaJy;
        String valueOf6 = String.valueOf("\t");
        String str = this.zzaJs;
        if (str == null) {
            str = "";
        }
        String valueOf7 = String.valueOf("\t");
        String str2 = this.zzaJz;
        if (str2 == null) {
            str2 = "";
        }
        String valueOf8 = String.valueOf("\t");
        float f = this.zzaJA;
        String valueOf9 = String.valueOf("\t");
        float f2 = f;
        String str3 = this.zzaJt;
        if (str3 == null) {
            str3 = "";
        }
        String str4 = str3;
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 37 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length() + String.valueOf(valueOf4).length() + String.valueOf(join).length() + String.valueOf(valueOf5).length() + String.valueOf(valueOf6).length() + String.valueOf(str).length() + String.valueOf(valueOf7).length() + String.valueOf(str2).length() + String.valueOf(valueOf8).length() + String.valueOf(valueOf9).length() + String.valueOf(str3).length());
        sb.append(valueOf);
        sb.append(valueOf2);
        sb.append(valueOf3);
        sb.append(i);
        sb.append(valueOf4);
        sb.append(join);
        sb.append(valueOf5);
        sb.append(i2);
        sb.append(valueOf6);
        sb.append(str);
        sb.append(valueOf7);
        sb.append(str2);
        sb.append(valueOf8);
        sb.append(f2);
        sb.append(valueOf9);
        sb.append(str4);
        return sb.toString();
    }
}
