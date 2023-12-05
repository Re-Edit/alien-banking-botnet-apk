package com.google.android.gms.common.api;

import android.os.Looper;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.internal.zzbcq;
import com.google.android.gms.internal.zzbfo;
import com.google.android.gms.internal.zzbfz;

public final class PendingResults {

    static final class zza<R extends Result> extends zzbcq<R> {
        private final R zzaBk;

        public zza(R r) {
            super(Looper.getMainLooper());
            this.zzaBk = r;
        }

        /* access modifiers changed from: protected */
        public final R zzb(Status status) {
            if (status.getStatusCode() == this.zzaBk.getStatus().getStatusCode()) {
                return this.zzaBk;
            }
            throw new UnsupportedOperationException("Creating failed results is not supported");
        }
    }

    static final class zzb<R extends Result> extends zzbcq<R> {
        private final R zzaBl;

        public zzb(GoogleApiClient googleApiClient, R r) {
            super(googleApiClient);
            this.zzaBl = r;
        }

        /* access modifiers changed from: protected */
        public final R zzb(Status status) {
            return this.zzaBl;
        }
    }

    static final class zzc<R extends Result> extends zzbcq<R> {
        public zzc(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* access modifiers changed from: protected */
        public final R zzb(Status status) {
            throw new UnsupportedOperationException("Creating failed results is not supported");
        }
    }

    private PendingResults() {
    }

    public static PendingResult<Status> canceledPendingResult() {
        zzbfz zzbfz = new zzbfz(Looper.getMainLooper());
        zzbfz.cancel();
        return zzbfz;
    }

    public static <R extends Result> PendingResult<R> canceledPendingResult(R r) {
        zzbr.zzb(r, (Object) "Result must not be null");
        zzbr.zzb(r.getStatus().getStatusCode() == 16, (Object) "Status code must be CommonStatusCodes.CANCELED");
        zza zza2 = new zza(r);
        zza2.cancel();
        return zza2;
    }

    public static <R extends Result> OptionalPendingResult<R> immediatePendingResult(R r) {
        zzbr.zzb(r, (Object) "Result must not be null");
        zzc zzc2 = new zzc((GoogleApiClient) null);
        zzc2.setResult(r);
        return new zzbfo(zzc2);
    }

    public static PendingResult<Status> immediatePendingResult(Status status) {
        zzbr.zzb(status, (Object) "Result must not be null");
        zzbfz zzbfz = new zzbfz(Looper.getMainLooper());
        zzbfz.setResult(status);
        return zzbfz;
    }

    public static <R extends Result> PendingResult<R> zza(R r, GoogleApiClient googleApiClient) {
        zzbr.zzb(r, (Object) "Result must not be null");
        zzbr.zzb(!r.getStatus().isSuccess(), (Object) "Status code must not be SUCCESS");
        zzb zzb2 = new zzb(googleApiClient, r);
        zzb2.setResult(r);
        return zzb2;
    }

    public static PendingResult<Status> zza(Status status, GoogleApiClient googleApiClient) {
        zzbr.zzb(status, (Object) "Result must not be null");
        zzbfz zzbfz = new zzbfz(googleApiClient);
        zzbfz.setResult(status);
        return zzbfz;
    }

    public static <R extends Result> OptionalPendingResult<R> zzb(R r, GoogleApiClient googleApiClient) {
        zzbr.zzb(r, (Object) "Result must not be null");
        zzc zzc2 = new zzc(googleApiClient);
        zzc2.setResult(r);
        return new zzbfo(zzc2);
    }
}
