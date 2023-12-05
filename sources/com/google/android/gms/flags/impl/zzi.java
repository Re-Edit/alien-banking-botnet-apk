package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import java.util.concurrent.Callable;

final class zzi implements Callable<String> {
    private /* synthetic */ SharedPreferences zzaXO;
    private /* synthetic */ String zzaXP;
    private /* synthetic */ String zzaXT;

    zzi(SharedPreferences sharedPreferences, String str, String str2) {
        this.zzaXO = sharedPreferences;
        this.zzaXP = str;
        this.zzaXT = str2;
    }

    public final /* synthetic */ Object call() throws Exception {
        return this.zzaXO.getString(this.zzaXP, this.zzaXT);
    }
}
