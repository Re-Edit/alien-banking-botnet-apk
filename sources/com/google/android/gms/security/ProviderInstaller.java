package com.google.android.gms.security;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.zze;
import com.google.android.gms.common.zzo;
import java.lang.reflect.Method;

public class ProviderInstaller {
    public static final String PROVIDER_NAME = "GmsCore_OpenSSL";
    /* access modifiers changed from: private */
    public static final zze zzbCK = zze.zzoU();
    private static Method zzbCL = null;
    private static final Object zzuI = new Object();

    public interface ProviderInstallListener {
        void onProviderInstallFailed(int i, Intent intent);

        void onProviderInstalled();
    }

    public static void installIfNeeded(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        zzbr.zzb(context, (Object) "Context must not be null");
        zze.zzas(context);
        Context remoteContext = zzo.getRemoteContext(context);
        if (remoteContext != null) {
            synchronized (zzuI) {
                try {
                    if (zzbCL == null) {
                        zzbCL = remoteContext.getClassLoader().loadClass("com.google.android.gms.common.security.ProviderInstallerImpl").getMethod("insertProvider", new Class[]{Context.class});
                    }
                    zzbCL.invoke((Object) null, new Object[]{remoteContext});
                } catch (Exception e) {
                    String valueOf = String.valueOf(e.getMessage());
                    Log.e("ProviderInstaller", valueOf.length() != 0 ? "Failed to install provider: ".concat(valueOf) : new String("Failed to install provider: "));
                    throw new GooglePlayServicesNotAvailableException(8);
                } catch (Throwable th) {
                    throw th;
                }
            }
            return;
        }
        Log.e("ProviderInstaller", "Failed to get remote context");
        throw new GooglePlayServicesNotAvailableException(8);
    }

    public static void installIfNeededAsync(Context context, ProviderInstallListener providerInstallListener) {
        zzbr.zzb(context, (Object) "Context must not be null");
        zzbr.zzb(providerInstallListener, (Object) "Listener must not be null");
        zzbr.zzcz("Must be called on the UI thread");
        new zza(context, providerInstallListener).execute(new Void[0]);
    }
}
