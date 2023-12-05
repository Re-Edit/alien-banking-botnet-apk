package com.google.android.gms.dynamic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

final class zze implements zzi {
    private /* synthetic */ FrameLayout zzaSB;
    private /* synthetic */ LayoutInflater zzaSC;
    private /* synthetic */ ViewGroup zzaSD;
    private /* synthetic */ zza zzaSz;
    private /* synthetic */ Bundle zzxY;

    zze(zza zza, FrameLayout frameLayout, LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.zzaSz = zza;
        this.zzaSB = frameLayout;
        this.zzaSC = layoutInflater;
        this.zzaSD = viewGroup;
        this.zzxY = bundle;
    }

    public final int getState() {
        return 2;
    }

    public final void zzb(LifecycleDelegate lifecycleDelegate) {
        this.zzaSB.removeAllViews();
        this.zzaSB.addView(this.zzaSz.zzaSv.onCreateView(this.zzaSC, this.zzaSD, this.zzxY));
    }
}
