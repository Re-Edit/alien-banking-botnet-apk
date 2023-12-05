package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule;

final class zzd implements DynamiteModule.zzd {
    zzd() {
    }

    public final zzi zza(Context context, String str, zzh zzh) throws DynamiteModule.zzc {
        int i;
        zzi zzi = new zzi();
        zzi.zzaSY = zzh.zzF(context, str);
        zzi.zzaSZ = zzh.zzb(context, str, true);
        if (zzi.zzaSY == 0 && zzi.zzaSZ == 0) {
            i = 0;
        } else if (zzi.zzaSY >= zzi.zzaSZ) {
            i = -1;
        } else {
            zzi.zzaTa = 1;
            return zzi;
        }
        zzi.zzaTa = i;
        return zzi;
    }
}
