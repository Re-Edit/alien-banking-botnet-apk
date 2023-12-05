package com.google.android.gms.internal;

import android.app.Activity;
import android.app.PendingIntent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzb;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.CancellationException;

public class zzbfn extends zzbcm {
    private TaskCompletionSource<Void> zzalG = new TaskCompletionSource<>();

    private zzbfn(zzbff zzbff) {
        super(zzbff);
        this.zzaEI.zza("GmsAvailabilityHelper", (zzbfe) this);
    }

    public static zzbfn zzp(Activity activity) {
        zzbff zzn = zzn(activity);
        zzbfn zzbfn = (zzbfn) zzn.zza("GmsAvailabilityHelper", zzbfn.class);
        if (zzbfn == null) {
            return new zzbfn(zzn);
        }
        if (zzbfn.zzalG.getTask().isComplete()) {
            zzbfn.zzalG = new TaskCompletionSource<>();
        }
        return zzbfn;
    }

    public final Task<Void> getTask() {
        return this.zzalG.getTask();
    }

    public final void onDestroy() {
        super.onDestroy();
        this.zzalG.setException(new CancellationException("Host activity was destroyed before Google Play services could be made available."));
    }

    /* access modifiers changed from: protected */
    public final void zza(ConnectionResult connectionResult, int i) {
        this.zzalG.setException(zzb.zzx(new Status(connectionResult.getErrorCode(), connectionResult.getErrorMessage(), connectionResult.getResolution())));
    }

    /* access modifiers changed from: protected */
    public final void zzpq() {
        int isGooglePlayServicesAvailable = this.zzaBf.isGooglePlayServicesAvailable(this.zzaEI.zzqD());
        if (isGooglePlayServicesAvailable == 0) {
            this.zzalG.setResult(null);
        } else if (!this.zzalG.getTask().isComplete()) {
            zzb(new ConnectionResult(isGooglePlayServicesAvailable, (PendingIntent) null), 0);
        }
    }
}
