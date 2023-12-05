package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.internal.zzs;
import com.google.android.gms.common.zze;
import com.google.android.gms.dynamic.LifecycleDelegate;
import java.util.LinkedList;

public abstract class zza<T extends LifecycleDelegate> {
    /* access modifiers changed from: private */
    public T zzaSv;
    /* access modifiers changed from: private */
    public Bundle zzaSw;
    /* access modifiers changed from: private */
    public LinkedList<zzi> zzaSx;
    private final zzo<T> zzaSy = new zzb(this);

    private final void zza(Bundle bundle, zzi zzi) {
        T t = this.zzaSv;
        if (t != null) {
            zzi.zzb(t);
            return;
        }
        if (this.zzaSx == null) {
            this.zzaSx = new LinkedList<>();
        }
        this.zzaSx.add(zzi);
        if (bundle != null) {
            Bundle bundle2 = this.zzaSw;
            if (bundle2 == null) {
                this.zzaSw = (Bundle) bundle.clone();
            } else {
                bundle2.putAll(bundle);
            }
        }
        zza(this.zzaSy);
    }

    private final void zzaR(int i) {
        while (!this.zzaSx.isEmpty() && this.zzaSx.getLast().getState() >= i) {
            this.zzaSx.removeLast();
        }
    }

    public static void zzb(FrameLayout frameLayout) {
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        Context context = frameLayout.getContext();
        int isGooglePlayServicesAvailable = instance.isGooglePlayServicesAvailable(context);
        String zzi = zzs.zzi(context, isGooglePlayServicesAvailable);
        String zzk = zzs.zzk(context, isGooglePlayServicesAvailable);
        LinearLayout linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        frameLayout.addView(linearLayout);
        TextView textView = new TextView(frameLayout.getContext());
        textView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        textView.setText(zzi);
        linearLayout.addView(textView);
        Intent zza = zze.zza(context, isGooglePlayServicesAvailable, (String) null);
        if (zza != null) {
            Button button = new Button(context);
            button.setId(16908313);
            button.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
            button.setText(zzk);
            linearLayout.addView(button);
            button.setOnClickListener(new zzf(context, zza));
        }
    }

    public final void onCreate(Bundle bundle) {
        zza(bundle, (zzi) new zzd(this, bundle));
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FrameLayout frameLayout = new FrameLayout(layoutInflater.getContext());
        zza(bundle, (zzi) new zze(this, frameLayout, layoutInflater, viewGroup, bundle));
        if (this.zzaSv == null) {
            zza(frameLayout);
        }
        return frameLayout;
    }

    public final void onDestroy() {
        T t = this.zzaSv;
        if (t != null) {
            t.onDestroy();
        } else {
            zzaR(1);
        }
    }

    public final void onDestroyView() {
        T t = this.zzaSv;
        if (t != null) {
            t.onDestroyView();
        } else {
            zzaR(2);
        }
    }

    public final void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
        zza(bundle2, (zzi) new zzc(this, activity, bundle, bundle2));
    }

    public final void onLowMemory() {
        T t = this.zzaSv;
        if (t != null) {
            t.onLowMemory();
        }
    }

    public final void onPause() {
        T t = this.zzaSv;
        if (t != null) {
            t.onPause();
        } else {
            zzaR(5);
        }
    }

    public final void onResume() {
        zza((Bundle) null, (zzi) new zzh(this));
    }

    public final void onSaveInstanceState(Bundle bundle) {
        T t = this.zzaSv;
        if (t != null) {
            t.onSaveInstanceState(bundle);
            return;
        }
        Bundle bundle2 = this.zzaSw;
        if (bundle2 != null) {
            bundle.putAll(bundle2);
        }
    }

    public final void onStart() {
        zza((Bundle) null, (zzi) new zzg(this));
    }

    public final void onStop() {
        T t = this.zzaSv;
        if (t != null) {
            t.onStop();
        } else {
            zzaR(4);
        }
    }

    /* access modifiers changed from: protected */
    public void zza(FrameLayout frameLayout) {
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        Context context = frameLayout.getContext();
        int isGooglePlayServicesAvailable = instance.isGooglePlayServicesAvailable(context);
        String zzi = zzs.zzi(context, isGooglePlayServicesAvailable);
        String zzk = zzs.zzk(context, isGooglePlayServicesAvailable);
        LinearLayout linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        frameLayout.addView(linearLayout);
        TextView textView = new TextView(frameLayout.getContext());
        textView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        textView.setText(zzi);
        linearLayout.addView(textView);
        Intent zza = zze.zza(context, isGooglePlayServicesAvailable, (String) null);
        if (zza != null) {
            Button button = new Button(context);
            button.setId(16908313);
            button.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
            button.setText(zzk);
            linearLayout.addView(button);
            button.setOnClickListener(new zzf(context, zza));
        }
    }

    /* access modifiers changed from: protected */
    public abstract void zza(zzo<T> zzo);

    public final T zztw() {
        return this.zzaSv;
    }
}
