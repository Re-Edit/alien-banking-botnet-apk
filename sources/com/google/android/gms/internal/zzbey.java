package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.gms.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzcb;

@Deprecated
public final class zzbey {
    private static zzbey zzaED;
    private static final Object zzuI = new Object();
    private final String mAppId;
    private final Status zzaEE;
    private final boolean zzaEF;
    private final boolean zzaEG;

    private zzbey(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("google_app_measurement_enable", "integer", resources.getResourcePackageName(R.string.common_google_play_services_unknown_issue));
        boolean z = true;
        if (identifier != 0) {
            z = resources.getInteger(identifier) == 0 ? false : z;
            this.zzaEG = !z;
        } else {
            this.zzaEG = false;
        }
        this.zzaEF = z;
        String zzaD = zzbg.zzaD(context);
        zzaD = zzaD == null ? new zzcb(context).getString("google_app_id") : zzaD;
        if (TextUtils.isEmpty(zzaD)) {
            this.zzaEE = new Status(10, "Missing google app id value from from string resources with name google_app_id.");
            this.mAppId = null;
            return;
        }
        this.mAppId = zzaD;
        this.zzaEE = Status.zzaBo;
    }

    public static Status zzaz(Context context) {
        Status status;
        zzbr.zzb(context, (Object) "Context must not be null.");
        synchronized (zzuI) {
            if (zzaED == null) {
                zzaED = new zzbey(context);
            }
            status = zzaED.zzaEE;
        }
        return status;
    }

    private static zzbey zzcu(String str) {
        zzbey zzbey;
        synchronized (zzuI) {
            if (zzaED != null) {
                zzbey = zzaED;
            } else {
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 34);
                sb.append("Initialize must be called before ");
                sb.append(str);
                sb.append(".");
                throw new IllegalStateException(sb.toString());
            }
        }
        return zzbey;
    }

    public static String zzqy() {
        return zzcu("getGoogleAppId").mAppId;
    }

    public static boolean zzqz() {
        return zzcu("isMeasurementExplicitlyDisabled").zzaEG;
    }
}
