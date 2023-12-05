package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class zzbfe {
    protected final zzbff zzaEI;

    protected zzbfe(zzbff zzbff) {
        this.zzaEI = zzbff;
    }

    protected static zzbff zzb(zzbfd zzbfd) {
        return zzbfd.zzqA() ? zzbga.zza(zzbfd.zzqC()) : zzbfg.zzo(zzbfd.zzqB());
    }

    public static zzbff zzn(Activity activity) {
        return zzb(new zzbfd(activity));
    }

    @MainThread
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    public final Activity getActivity() {
        return this.zzaEI.zzqD();
    }

    @MainThread
    public void onActivityResult(int i, int i2, Intent intent) {
    }

    @MainThread
    public void onCreate(Bundle bundle) {
    }

    @MainThread
    public void onDestroy() {
    }

    @MainThread
    public void onResume() {
    }

    @MainThread
    public void onSaveInstanceState(Bundle bundle) {
    }

    @MainThread
    public void onStart() {
    }

    @MainThread
    public void onStop() {
    }
}
