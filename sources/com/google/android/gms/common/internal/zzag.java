package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Intent;
import java.util.Arrays;

public final class zzag {
    private final String zzaHP;
    private final ComponentName zzaHQ;
    private final String zzaeZ;

    public zzag(ComponentName componentName) {
        this.zzaeZ = null;
        this.zzaHP = null;
        this.zzaHQ = (ComponentName) zzbr.zzu(componentName);
    }

    public zzag(String str, String str2) {
        this.zzaeZ = zzbr.zzcF(str);
        this.zzaHP = zzbr.zzcF(str2);
        this.zzaHQ = null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzag)) {
            return false;
        }
        zzag zzag = (zzag) obj;
        return zzbh.equal(this.zzaeZ, zzag.zzaeZ) && zzbh.equal(this.zzaHP, zzag.zzaHP) && zzbh.equal(this.zzaHQ, zzag.zzaHQ);
    }

    public final ComponentName getComponentName() {
        return this.zzaHQ;
    }

    public final String getPackage() {
        return this.zzaHP;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzaeZ, this.zzaHP, this.zzaHQ});
    }

    public final String toString() {
        String str = this.zzaeZ;
        return str == null ? this.zzaHQ.flattenToString() : str;
    }

    public final Intent zzrA() {
        String str = this.zzaeZ;
        return str != null ? new Intent(str).setPackage(this.zzaHP) : new Intent().setComponent(this.zzaHQ);
    }
}
