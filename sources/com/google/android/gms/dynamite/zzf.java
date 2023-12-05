package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule;

final class zzf implements DynamiteModule.zzd {
    zzf() {
    }

    public final zzi zza(Context context, String str, zzh zzh) throws DynamiteModule.zzc {
        zzi zzi = new zzi();
        zzi.zzaSY = zzh.zzF(context, str);
        zzi.zzaSZ = zzi.zzaSY != 0 ? zzh.zzb(context, str, false) : zzh.zzb(context, str, true);
        if (zzi.zzaSY == 0 && zzi.zzaSZ == 0) {
            zzi.zzaTa = 0;
        } else if (zzi.zzaSZ >= zzi.zzaSY) {
            zzi.zzaTa = 1;
        } else {
            zzi.zzaTa = -1;
        }
        return zzi;
    }
}
