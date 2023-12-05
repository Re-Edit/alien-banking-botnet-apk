package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbh;
import java.util.Arrays;

public final class Status extends zza implements Result, ReflectedParcelable {
    public static final Parcelable.Creator<Status> CREATOR = new zzf();
    public static final Status zzaBo = new Status(0);
    public static final Status zzaBp = new Status(14);
    public static final Status zzaBq = new Status(8);
    public static final Status zzaBr = new Status(15);
    public static final Status zzaBs = new Status(16);
    private static Status zzaBt = new Status(17);
    private static Status zzaBu = new Status(18);
    private final PendingIntent mPendingIntent;
    private final String zzaAa;
    private int zzakw;
    private final int zzaxw;

    public Status(int i) {
        this(i, (String) null);
    }

    Status(int i, int i2, String str, PendingIntent pendingIntent) {
        this.zzakw = i;
        this.zzaxw = i2;
        this.zzaAa = str;
        this.mPendingIntent = pendingIntent;
    }

    public Status(int i, String str) {
        this(1, i, str, (PendingIntent) null);
    }

    public Status(int i, String str, PendingIntent pendingIntent) {
        this(1, i, str, pendingIntent);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof Status)) {
            return false;
        }
        Status status = (Status) obj;
        return this.zzakw == status.zzakw && this.zzaxw == status.zzaxw && zzbh.equal(this.zzaAa, status.zzaAa) && zzbh.equal(this.mPendingIntent, status.mPendingIntent);
    }

    public final PendingIntent getResolution() {
        return this.mPendingIntent;
    }

    public final Status getStatus() {
        return this;
    }

    public final int getStatusCode() {
        return this.zzaxw;
    }

    @Nullable
    public final String getStatusMessage() {
        return this.zzaAa;
    }

    public final boolean hasResolution() {
        return this.mPendingIntent != null;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zzakw), Integer.valueOf(this.zzaxw), this.zzaAa, this.mPendingIntent});
    }

    public final boolean isCanceled() {
        return this.zzaxw == 16;
    }

    public final boolean isInterrupted() {
        return this.zzaxw == 14;
    }

    public final boolean isSuccess() {
        return this.zzaxw <= 0;
    }

    public final void startResolutionForResult(Activity activity, int i) throws IntentSender.SendIntentException {
        if (hasResolution()) {
            activity.startIntentSenderForResult(this.mPendingIntent.getIntentSender(), i, (Intent) null, 0, 0, 0);
        }
    }

    public final String toString() {
        return zzbh.zzt(this).zzg("statusCode", zzpo()).zzg("resolution", this.mPendingIntent).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, getStatusCode());
        zzd.zza(parcel, 2, getStatusMessage(), false);
        zzd.zza(parcel, 3, (Parcelable) this.mPendingIntent, i, false);
        zzd.zzc(parcel, 1000, this.zzakw);
        zzd.zzI(parcel, zze);
    }

    public final String zzpo() {
        String str = this.zzaAa;
        return str != null ? str : CommonStatusCodes.getStatusCodeString(this.zzaxw);
    }
}
