package com.google.android.gms.internal;

import android.os.SystemClock;
import android.util.SparseArray;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.zzctm;
import com.google.android.gms.safetynet.SafeBrowsingData;

final class zzcty extends zzctg {
    private /* synthetic */ zzctm.zzf zzbCf;

    zzcty(zzctm.zzf zzf) {
        this.zzbCf = zzf;
    }

    public final void zza(Status status, SafeBrowsingData safeBrowsingData) {
        DataHolder blacklistsDataHolder = safeBrowsingData.getBlacklistsDataHolder();
        if (blacklistsDataHolder != null) {
            try {
                int count = blacklistsDataHolder.getCount();
                if (count != 0) {
                    if (zzctm.zzbBQ != null) {
                        zzctm.zzbBQ.clear();
                    }
                    zzctm.zzbBQ = new SparseArray<>();
                    for (int i = 0; i < count; i++) {
                        zzcud zzcud = new zzcud(blacklistsDataHolder, i);
                        zzctm.zzbBQ.put(zzcud.getThreatType(), zzcud);
                    }
                    zzctm.zzbBR = SystemClock.elapsedRealtime();
                }
            } finally {
                if (!blacklistsDataHolder.isClosed()) {
                    blacklistsDataHolder.close();
                }
            }
        }
        this.zzbCf.setResult(new zzctm.zzi(status, safeBrowsingData));
    }
}
