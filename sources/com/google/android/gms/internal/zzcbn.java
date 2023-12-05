package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.dynamite.descriptors.com.google.android.gms.flags.ModuleDescriptor;

public final class zzcbn {
    private zzcbo zzaXK = null;
    private boolean zzuK = false;

    public final void initialize(Context context) {
        synchronized (this) {
            if (!this.zzuK) {
                try {
                    this.zzaXK = zzcbp.asInterface(DynamiteModule.zza(context, DynamiteModule.zzaST, ModuleDescriptor.MODULE_ID).zzcW("com.google.android.gms.flags.impl.FlagProviderImpl"));
                    this.zzaXK.init(zzn.zzw(context));
                    this.zzuK = true;
                } catch (RemoteException | DynamiteModule.zzc e) {
                    Log.w("FlagValueProvider", "Failed to initialize flags module.", e);
                }
            }
        }
    }

    public final <T> T zzb(zzcbg<T> zzcbg) {
        synchronized (this) {
            if (this.zzuK) {
                return zzcbg.zza(this.zzaXK);
            }
            T zzdH = zzcbg.zzdH();
            return zzdH;
        }
    }
}
