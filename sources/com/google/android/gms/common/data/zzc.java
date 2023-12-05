package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.internal.zzbh;
import com.google.android.gms.common.internal.zzbr;
import java.util.Arrays;

public class zzc {
    protected final DataHolder zzaCZ;
    private int zzaFA;
    protected int zzaFz;

    public zzc(DataHolder dataHolder, int i) {
        this.zzaCZ = (DataHolder) zzbr.zzu(dataHolder);
        zzar(i);
    }

    public boolean equals(Object obj) {
        if (obj instanceof zzc) {
            zzc zzc = (zzc) obj;
            return zzbh.equal(Integer.valueOf(zzc.zzaFz), Integer.valueOf(this.zzaFz)) && zzbh.equal(Integer.valueOf(zzc.zzaFA), Integer.valueOf(this.zzaFA)) && zzc.zzaCZ == this.zzaCZ;
        }
    }

    /* access modifiers changed from: protected */
    public final boolean getBoolean(String str) {
        return this.zzaCZ.zze(str, this.zzaFz, this.zzaFA);
    }

    /* access modifiers changed from: protected */
    public final byte[] getByteArray(String str) {
        return this.zzaCZ.zzg(str, this.zzaFz, this.zzaFA);
    }

    /* access modifiers changed from: protected */
    public final float getFloat(String str) {
        return this.zzaCZ.zzf(str, this.zzaFz, this.zzaFA);
    }

    /* access modifiers changed from: protected */
    public final int getInteger(String str) {
        return this.zzaCZ.zzc(str, this.zzaFz, this.zzaFA);
    }

    /* access modifiers changed from: protected */
    public final long getLong(String str) {
        return this.zzaCZ.zzb(str, this.zzaFz, this.zzaFA);
    }

    /* access modifiers changed from: protected */
    public final String getString(String str) {
        return this.zzaCZ.zzd(str, this.zzaFz, this.zzaFA);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zzaFz), Integer.valueOf(this.zzaFA), this.zzaCZ});
    }

    public boolean isDataValid() {
        return !this.zzaCZ.isClosed();
    }

    /* access modifiers changed from: protected */
    public final void zza(String str, CharArrayBuffer charArrayBuffer) {
        this.zzaCZ.zza(str, this.zzaFz, this.zzaFA, charArrayBuffer);
    }

    /* access modifiers changed from: protected */
    public final void zzar(int i) {
        zzbr.zzae(i >= 0 && i < this.zzaCZ.zzaFI);
        this.zzaFz = i;
        this.zzaFA = this.zzaCZ.zzat(this.zzaFz);
    }

    public final boolean zzcv(String str) {
        return this.zzaCZ.zzcv(str);
    }

    /* access modifiers changed from: protected */
    public final Uri zzcw(String str) {
        String zzd = this.zzaCZ.zzd(str, this.zzaFz, this.zzaFA);
        if (zzd == null) {
            return null;
        }
        return Uri.parse(zzd);
    }

    /* access modifiers changed from: protected */
    public final boolean zzcx(String str) {
        return this.zzaCZ.zzh(str, this.zzaFz, this.zzaFA);
    }
}
