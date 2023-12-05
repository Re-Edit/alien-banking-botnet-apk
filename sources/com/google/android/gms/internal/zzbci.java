package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;
import com.google.android.gms.common.util.zzs;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public final class zzbci implements Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {
    private static final zzbci zzaBL = new zzbci();
    private final ArrayList<zzbcj> mListeners = new ArrayList<>();
    private final AtomicBoolean zzaBM = new AtomicBoolean();
    private final AtomicBoolean zzaBN = new AtomicBoolean();
    private boolean zzafM = false;

    private zzbci() {
    }

    public static void zza(Application application) {
        synchronized (zzaBL) {
            if (!zzaBL.zzafM) {
                application.registerActivityLifecycleCallbacks(zzaBL);
                application.registerComponentCallbacks(zzaBL);
                zzaBL.zzafM = true;
            }
        }
    }

    private final void zzac(boolean z) {
        synchronized (zzaBL) {
            ArrayList arrayList = this.mListeners;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((zzbcj) obj).zzac(z);
            }
        }
    }

    public static zzbci zzpt() {
        return zzaBL;
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        boolean compareAndSet = this.zzaBM.compareAndSet(true, false);
        this.zzaBN.set(true);
        if (compareAndSet) {
            zzac(false);
        }
    }

    public final void onActivityDestroyed(Activity activity) {
    }

    public final void onActivityPaused(Activity activity) {
    }

    public final void onActivityResumed(Activity activity) {
        boolean compareAndSet = this.zzaBM.compareAndSet(true, false);
        this.zzaBN.set(true);
        if (compareAndSet) {
            zzac(false);
        }
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public final void onActivityStarted(Activity activity) {
    }

    public final void onActivityStopped(Activity activity) {
    }

    public final void onConfigurationChanged(Configuration configuration) {
    }

    public final void onLowMemory() {
    }

    public final void onTrimMemory(int i) {
        if (i == 20 && this.zzaBM.compareAndSet(false, true)) {
            this.zzaBN.set(true);
            zzac(true);
        }
    }

    public final void zza(zzbcj zzbcj) {
        synchronized (zzaBL) {
            this.mListeners.add(zzbcj);
        }
    }

    @TargetApi(16)
    public final boolean zzab(boolean z) {
        if (!this.zzaBN.get()) {
            if (!zzs.zzrY()) {
                return true;
            }
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
            ActivityManager.getMyMemoryState(runningAppProcessInfo);
            if (!this.zzaBN.getAndSet(true) && runningAppProcessInfo.importance > 100) {
                this.zzaBM.set(true);
            }
        }
        return this.zzaBM.get();
    }

    public final boolean zzpu() {
        return this.zzaBM.get();
    }
}
