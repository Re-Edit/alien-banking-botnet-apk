package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import java.util.concurrent.Callable;

final class zze implements Callable<Integer> {
    private /* synthetic */ SharedPreferences zzaXO;
    private /* synthetic */ String zzaXP;
    private /* synthetic */ Integer zzaXR;

    zze(SharedPreferences sharedPreferences, String str, Integer num) {
        this.zzaXO = sharedPreferences;
        this.zzaXP = str;
        this.zzaXR = num;
    }

    public final /* synthetic */ Object call() throws Exception {
        return Integer.valueOf(this.zzaXO.getInt(this.zzaXP, this.zzaXR.intValue()));
    }
}
