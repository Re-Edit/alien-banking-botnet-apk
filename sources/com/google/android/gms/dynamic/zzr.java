package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

public final class zzr extends zzl {
    private Fragment zzaSI;

    private zzr(Fragment fragment) {
        this.zzaSI = fragment;
    }

    public static zzr zza(Fragment fragment) {
        if (fragment != null) {
            return new zzr(fragment);
        }
        return null;
    }

    public final Bundle getArguments() {
        return this.zzaSI.getArguments();
    }

    public final int getId() {
        return this.zzaSI.getId();
    }

    public final boolean getRetainInstance() {
        return this.zzaSI.getRetainInstance();
    }

    public final String getTag() {
        return this.zzaSI.getTag();
    }

    public final int getTargetRequestCode() {
        return this.zzaSI.getTargetRequestCode();
    }

    public final boolean getUserVisibleHint() {
        return this.zzaSI.getUserVisibleHint();
    }

    public final IObjectWrapper getView() {
        return zzn.zzw(this.zzaSI.getView());
    }

    public final boolean isAdded() {
        return this.zzaSI.isAdded();
    }

    public final boolean isDetached() {
        return this.zzaSI.isDetached();
    }

    public final boolean isHidden() {
        return this.zzaSI.isHidden();
    }

    public final boolean isInLayout() {
        return this.zzaSI.isInLayout();
    }

    public final boolean isRemoving() {
        return this.zzaSI.isRemoving();
    }

    public final boolean isResumed() {
        return this.zzaSI.isResumed();
    }

    public final boolean isVisible() {
        return this.zzaSI.isVisible();
    }

    public final void setHasOptionsMenu(boolean z) {
        this.zzaSI.setHasOptionsMenu(z);
    }

    public final void setMenuVisibility(boolean z) {
        this.zzaSI.setMenuVisibility(z);
    }

    public final void setRetainInstance(boolean z) {
        this.zzaSI.setRetainInstance(z);
    }

    public final void setUserVisibleHint(boolean z) {
        this.zzaSI.setUserVisibleHint(z);
    }

    public final void startActivity(Intent intent) {
        this.zzaSI.startActivity(intent);
    }

    public final void startActivityForResult(Intent intent, int i) {
        this.zzaSI.startActivityForResult(intent, i);
    }

    public final void zzC(IObjectWrapper iObjectWrapper) {
        this.zzaSI.registerForContextMenu((View) zzn.zzE(iObjectWrapper));
    }

    public final void zzD(IObjectWrapper iObjectWrapper) {
        this.zzaSI.unregisterForContextMenu((View) zzn.zzE(iObjectWrapper));
    }

    public final zzk zztA() {
        return zza(this.zzaSI.getTargetFragment());
    }

    public final IObjectWrapper zztx() {
        return zzn.zzw(this.zzaSI.getActivity());
    }

    public final zzk zzty() {
        return zza(this.zzaSI.getParentFragment());
    }

    public final IObjectWrapper zztz() {
        return zzn.zzw(this.zzaSI.getResources());
    }
}
