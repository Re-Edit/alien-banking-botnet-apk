package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule;

final class zzb implements DynamiteModule.zzd {
    zzb() {
    }

    public final zzi zza(Context context, String str, zzh zzh) throws DynamiteModule.zzc {
        zzi zzi = new zzi();
        zzi.zzaSZ = zzh.zzb(context, str, true);
        if (zzi.zzaSZ != 0) {
            zzi.zzaTa = 1;
        } else {
            zzi.zzaSY = zzh.zzF(context, str);
            if (zzi.zzaSY != 0) {
                zzi.zzaTa = -1;
            }
        }
        return zzi;
    }
}
