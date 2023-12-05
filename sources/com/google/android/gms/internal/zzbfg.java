package com.google.android.gms.internal;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

public final class zzbfg extends Fragment implements zzbff {
    private static WeakHashMap<Activity, WeakReference<zzbfg>> zzaEJ = new WeakHashMap<>();
    /* access modifiers changed from: private */
    public int zzLj = 0;
    private Map<String, zzbfe> zzaEK = new ArrayMap();
    /* access modifiers changed from: private */
    public Bundle zzaEL;

    public static zzbfg zzo(Activity activity) {
        zzbfg zzbfg;
        WeakReference weakReference = zzaEJ.get(activity);
        if (weakReference != null && (zzbfg = (zzbfg) weakReference.get()) != null) {
            return zzbfg;
        }
        try {
            zzbfg zzbfg2 = (zzbfg) activity.getFragmentManager().findFragmentByTag("LifecycleFragmentImpl");
            if (zzbfg2 == null || zzbfg2.isRemoving()) {
                zzbfg2 = new zzbfg();
                activity.getFragmentManager().beginTransaction().add(zzbfg2, "LifecycleFragmentImpl").commitAllowingStateLoss();
            }
            zzaEJ.put(activity, new WeakReference(zzbfg2));
            return zzbfg2;
        } catch (ClassCastException e) {
            throw new IllegalStateException("Fragment with tag LifecycleFragmentImpl is not a LifecycleFragmentImpl", e);
        }
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        for (zzbfe dump : this.zzaEK.values()) {
            dump.dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        for (zzbfe onActivityResult : this.zzaEK.values()) {
            onActivityResult.onActivityResult(i, i2, intent);
        }
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.zzLj = 1;
        this.zzaEL = bundle;
        for (Map.Entry next : this.zzaEK.entrySet()) {
            ((zzbfe) next.getValue()).onCreate(bundle != null ? bundle.getBundle((String) next.getKey()) : null);
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        this.zzLj = 5;
        for (zzbfe onDestroy : this.zzaEK.values()) {
            onDestroy.onDestroy();
        }
    }

    public final void onResume() {
        super.onResume();
        this.zzLj = 3;
        for (zzbfe onResume : this.zzaEK.values()) {
            onResume.onResume();
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (bundle != null) {
            for (Map.Entry next : this.zzaEK.entrySet()) {
                Bundle bundle2 = new Bundle();
                ((zzbfe) next.getValue()).onSaveInstanceState(bundle2);
                bundle.putBundle((String) next.getKey(), bundle2);
            }
        }
    }

    public final void onStart() {
        super.onStart();
        this.zzLj = 2;
        for (zzbfe onStart : this.zzaEK.values()) {
            onStart.onStart();
        }
    }

    public final void onStop() {
        super.onStop();
        this.zzLj = 4;
        for (zzbfe onStop : this.zzaEK.values()) {
            onStop.onStop();
        }
    }

    public final <T extends zzbfe> T zza(String str, Class<T> cls) {
        return (zzbfe) cls.cast(this.zzaEK.get(str));
    }

    public final void zza(String str, @NonNull zzbfe zzbfe) {
        if (!this.zzaEK.containsKey(str)) {
            this.zzaEK.put(str, zzbfe);
            if (this.zzLj > 0) {
                new Handler(Looper.getMainLooper()).post(new zzbfh(this, zzbfe, str));
                return;
            }
            return;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 59);
        sb.append("LifecycleCallback with tag ");
        sb.append(str);
        sb.append(" already added to this fragment.");
        throw new IllegalArgumentException(sb.toString());
    }

    public final Activity zzqD() {
        return getActivity();
    }
}
