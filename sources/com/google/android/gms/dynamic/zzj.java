package com.google.android.gms.dynamic;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

@SuppressLint({"NewApi"})
public final class zzj extends zzl {
    private Fragment zzaSF;

    private zzj(Fragment fragment) {
        this.zzaSF = fragment;
    }

    public static zzj zza(Fragment fragment) {
        if (fragment != null) {
            return new zzj(fragment);
        }
        return null;
    }

    public final Bundle getArguments() {
        return this.zzaSF.getArguments();
    }

    public final int getId() {
        return this.zzaSF.getId();
    }

    public final boolean getRetainInstance() {
        return this.zzaSF.getRetainInstance();
    }

    public final String getTag() {
        return this.zzaSF.getTag();
    }

    public final int getTargetRequestCode() {
        return this.zzaSF.getTargetRequestCode();
    }

    public final boolean getUserVisibleHint() {
        return this.zzaSF.getUserVisibleHint();
    }

    public final IObjectWrapper getView() {
        return zzn.zzw(this.zzaSF.getView());
    }

    public final boolean isAdded() {
        return this.zzaSF.isAdded();
    }

    public final boolean isDetached() {
        return this.zzaSF.isDetached();
    }

    public final boolean isHidden() {
        return this.zzaSF.isHidden();
    }

    public final boolean isInLayout() {
        return this.zzaSF.isInLayout();
    }

    public final boolean isRemoving() {
        return this.zzaSF.isRemoving();
    }

    public final boolean isResumed() {
        return this.zzaSF.isResumed();
    }

    public final boolean isVisible() {
        return this.zzaSF.isVisible();
    }

    public final void setHasOptionsMenu(boolean z) {
        this.zzaSF.setHasOptionsMenu(z);
    }

    public final void setMenuVisibility(boolean z) {
        this.zzaSF.setMenuVisibility(z);
    }

    public final void setRetainInstance(boolean z) {
        this.zzaSF.setRetainInstance(z);
    }

    public final void setUserVisibleHint(boolean z) {
        this.zzaSF.setUserVisibleHint(z);
    }

    public final void startActivity(Intent intent) {
        this.zzaSF.startActivity(intent);
    }

    public final void startActivityForResult(Intent intent, int i) {
        this.zzaSF.startActivityForResult(intent, i);
    }

    public final void zzC(IObjectWrapper iObjectWrapper) {
        this.zzaSF.registerForContextMenu((View) zzn.zzE(iObjectWrapper));
    }

    public final void zzD(IObjectWrapper iObjectWrapper) {
        this.zzaSF.unregisterForContextMenu((View) zzn.zzE(iObjectWrapper));
    }

    public final zzk zztA() {
        return zza(this.zzaSF.getTargetFragment());
    }

    public final IObjectWrapper zztx() {
        return zzn.zzw(this.zzaSF.getActivity());
    }

    public final zzk zzty() {
        return zza(this.zzaSF.getParentFragment());
    }

    public final IObjectWrapper zztz() {
        return zzn.zzw(this.zzaSF.getResources());
    }
}
