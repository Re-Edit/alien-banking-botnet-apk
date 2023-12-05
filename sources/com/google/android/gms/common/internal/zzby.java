package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.view.View;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamic.zzp;
import com.google.android.gms.dynamic.zzq;

public final class zzby extends zzp<zzbe> {
    private static final zzby zzaIx = new zzby();

    private zzby() {
        super("com.google.android.gms.common.ui.SignInButtonCreatorImpl");
    }

    public static View zzc(Context context, int i, int i2) throws zzq {
        return zzaIx.zzd(context, i, i2);
    }

    private final View zzd(Context context, int i, int i2) throws zzq {
        try {
            zzbw zzbw = new zzbw(i, i2, (Scope[]) null);
            return (View) zzn.zzE(((zzbe) zzaS(context)).zza(zzn.zzw(context), zzbw));
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder(64);
            sb.append("Could not get button with size ");
            sb.append(i);
            sb.append(" and color ");
            sb.append(i2);
            throw new zzq(sb.toString(), e);
        }
    }

    public final /* synthetic */ Object zzb(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
        return queryLocalInterface instanceof zzbe ? (zzbe) queryLocalInterface : new zzbf(iBinder);
    }
}
