package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class zzbdl extends GoogleApiClient {
    private final UnsupportedOperationException zzaDa;

    public zzbdl(String str) {
        this.zzaDa = new UnsupportedOperationException(str);
    }

    public ConnectionResult blockingConnect() {
        throw this.zzaDa;
    }

    public ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) {
        throw this.zzaDa;
    }

    public PendingResult<Status> clearDefaultAccountAndReconnect() {
        throw this.zzaDa;
    }

    public void connect() {
        throw this.zzaDa;
    }

    public void disconnect() {
        throw this.zzaDa;
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        throw this.zzaDa;
    }

    @NonNull
    public ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        throw this.zzaDa;
    }

    public boolean hasConnectedApi(@NonNull Api<?> api) {
        throw this.zzaDa;
    }

    public boolean isConnected() {
        throw this.zzaDa;
    }

    public boolean isConnecting() {
        throw this.zzaDa;
    }

    public boolean isConnectionCallbacksRegistered(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        throw this.zzaDa;
    }

    public boolean isConnectionFailedListenerRegistered(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        throw this.zzaDa;
    }

    public void reconnect() {
        throw this.zzaDa;
    }

    public void registerConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        throw this.zzaDa;
    }

    public void registerConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        throw this.zzaDa;
    }

    public void stopAutoManage(@NonNull FragmentActivity fragmentActivity) {
        throw this.zzaDa;
    }

    public void unregisterConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        throw this.zzaDa;
    }

    public void unregisterConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        throw this.zzaDa;
    }
}
