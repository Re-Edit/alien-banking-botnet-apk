package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public interface zzbfb {
    ConnectionResult blockingConnect();

    ConnectionResult blockingConnect(long j, TimeUnit timeUnit);

    void connect();

    void disconnect();

    void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    @Nullable
    ConnectionResult getConnectionResult(@NonNull Api<?> api);

    boolean isConnected();

    boolean isConnecting();

    boolean zza(zzbfu zzbfu);

    <A extends Api.zzb, R extends Result, T extends zzbck<R, A>> T zzd(@NonNull T t);

    <A extends Api.zzb, T extends zzbck<? extends Result, A>> T zze(@NonNull T t);

    void zzpC();

    void zzpj();
}
