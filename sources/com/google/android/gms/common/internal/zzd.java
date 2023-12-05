package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zze;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class zzd<T extends IInterface> {
    private static String[] zzaHe = {"service_esmobile", "service_googleme"};
    private final Context mContext;
    final Handler mHandler;
    private final Object mLock;
    private final zze zzaCH;
    private int zzaGJ;
    private long zzaGK;
    private long zzaGL;
    private int zzaGM;
    private long zzaGN;
    private zzal zzaGO;
    private final zzaf zzaGP;
    /* access modifiers changed from: private */
    public final Object zzaGQ;
    /* access modifiers changed from: private */
    public zzay zzaGR;
    protected zzj zzaGS;
    private T zzaGT;
    /* access modifiers changed from: private */
    public final ArrayList<zzi<?>> zzaGU;
    private zzl zzaGV;
    private int zzaGW;
    /* access modifiers changed from: private */
    public final zzf zzaGX;
    /* access modifiers changed from: private */
    public final zzg zzaGY;
    private final int zzaGZ;
    private final String zzaHa;
    /* access modifiers changed from: private */
    public ConnectionResult zzaHb;
    /* access modifiers changed from: private */
    public boolean zzaHc;
    protected AtomicInteger zzaHd;
    private final Looper zzrP;

    protected zzd(Context context, Looper looper, int i, zzf zzf, zzg zzg, String str) {
        this(context, looper, zzaf.zzaC(context), zze.zzoU(), i, (zzf) zzbr.zzu(zzf), (zzg) zzbr.zzu(zzg), (String) null);
    }

    protected zzd(Context context, Looper looper, zzaf zzaf, zze zze, int i, zzf zzf, zzg zzg, String str) {
        this.mLock = new Object();
        this.zzaGQ = new Object();
        this.zzaGU = new ArrayList<>();
        this.zzaGW = 1;
        this.zzaHb = null;
        this.zzaHc = false;
        this.zzaHd = new AtomicInteger(0);
        this.mContext = (Context) zzbr.zzb(context, (Object) "Context must not be null");
        this.zzrP = (Looper) zzbr.zzb(looper, (Object) "Looper must not be null");
        this.zzaGP = (zzaf) zzbr.zzb(zzaf, (Object) "Supervisor must not be null");
        this.zzaCH = (zze) zzbr.zzb(zze, (Object) "API availability must not be null");
        this.mHandler = new zzh(this, looper);
        this.zzaGZ = i;
        this.zzaGX = zzf;
        this.zzaGY = zzg;
        this.zzaHa = str;
    }

    /* access modifiers changed from: private */
    public final void zza(int i, T t) {
        boolean z = true;
        if ((i == 4) != (t != null)) {
            z = false;
        }
        zzbr.zzaf(z);
        synchronized (this.mLock) {
            this.zzaGW = i;
            this.zzaGT = t;
            switch (i) {
                case 1:
                    if (this.zzaGV != null) {
                        this.zzaGP.zza(zzda(), zzqX(), this.zzaGV, zzqY());
                        this.zzaGV = null;
                        break;
                    }
                    break;
                case 2:
                case 3:
                    if (!(this.zzaGV == null || this.zzaGO == null)) {
                        String valueOf = String.valueOf(this.zzaGO.zzrD());
                        String valueOf2 = String.valueOf(this.zzaGO.getPackageName());
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 70 + String.valueOf(valueOf2).length());
                        sb.append("Calling connect() while still connected, missing disconnect() for ");
                        sb.append(valueOf);
                        sb.append(" on ");
                        sb.append(valueOf2);
                        Log.e("GmsClient", sb.toString());
                        this.zzaGP.zza(this.zzaGO.zzrD(), this.zzaGO.getPackageName(), this.zzaGV, zzqY());
                        this.zzaHd.incrementAndGet();
                    }
                    this.zzaGV = new zzl(this, this.zzaHd.get());
                    this.zzaGO = new zzal(zzqX(), zzda(), false);
                    if (!this.zzaGP.zza(new zzag(this.zzaGO.zzrD(), this.zzaGO.getPackageName()), (ServiceConnection) this.zzaGV, zzqY())) {
                        String valueOf3 = String.valueOf(this.zzaGO.zzrD());
                        String valueOf4 = String.valueOf(this.zzaGO.getPackageName());
                        StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf3).length() + 34 + String.valueOf(valueOf4).length());
                        sb2.append("unable to connect to service: ");
                        sb2.append(valueOf3);
                        sb2.append(" on ");
                        sb2.append(valueOf4);
                        Log.e("GmsClient", sb2.toString());
                        zza(16, (Bundle) null, this.zzaHd.get());
                        break;
                    }
                    break;
                case 4:
                    zza(t);
                    break;
            }
        }
    }

    /* access modifiers changed from: private */
    public final boolean zza(int i, int i2, T t) {
        synchronized (this.mLock) {
            if (this.zzaGW != i) {
                return false;
            }
            zza(i2, t);
            return true;
        }
    }

    /* access modifiers changed from: private */
    public final void zzaz(int i) {
        int i2;
        if (zzra()) {
            i2 = 5;
            this.zzaHc = true;
        } else {
            i2 = 4;
        }
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(i2, this.zzaHd.get(), 16));
    }

    @Nullable
    private final String zzqY() {
        String str = this.zzaHa;
        return str == null ? this.mContext.getClass().getName() : str;
    }

    private final boolean zzra() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzaGW == 3;
        }
        return z;
    }

    /* access modifiers changed from: private */
    public final boolean zzrg() {
        if (this.zzaHc || TextUtils.isEmpty(zzdb()) || TextUtils.isEmpty((CharSequence) null)) {
            return false;
        }
        try {
            Class.forName(zzdb());
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public void disconnect() {
        this.zzaHd.incrementAndGet();
        synchronized (this.zzaGU) {
            int size = this.zzaGU.size();
            for (int i = 0; i < size; i++) {
                this.zzaGU.get(i).removeListener();
            }
            this.zzaGU.clear();
        }
        synchronized (this.zzaGQ) {
            this.zzaGR = null;
        }
        zza(1, (IInterface) null);
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int i;
        T t;
        zzay zzay;
        String str2;
        String str3;
        synchronized (this.mLock) {
            i = this.zzaGW;
            t = this.zzaGT;
        }
        synchronized (this.zzaGQ) {
            zzay = this.zzaGR;
        }
        printWriter.append(str).append("mConnectState=");
        switch (i) {
            case 1:
                str2 = "DISCONNECTED";
                break;
            case 2:
                str2 = "REMOTE_CONNECTING";
                break;
            case 3:
                str2 = "LOCAL_CONNECTING";
                break;
            case 4:
                str2 = "CONNECTED";
                break;
            case 5:
                str2 = "DISCONNECTING";
                break;
            default:
                str2 = "UNKNOWN";
                break;
        }
        printWriter.print(str2);
        printWriter.append(" mService=");
        if (t == null) {
            printWriter.append("null");
        } else {
            printWriter.append(zzdb()).append("@").append(Integer.toHexString(System.identityHashCode(t.asBinder())));
        }
        printWriter.append(" mServiceBroker=");
        if (zzay == null) {
            printWriter.println("null");
        } else {
            printWriter.append("IGmsServiceBroker@").println(Integer.toHexString(System.identityHashCode(zzay.asBinder())));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        if (this.zzaGL > 0) {
            PrintWriter append = printWriter.append(str).append("lastConnectedTime=");
            long j = this.zzaGL;
            String valueOf = String.valueOf(simpleDateFormat.format(new Date(j)));
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 21);
            sb.append(j);
            sb.append(" ");
            sb.append(valueOf);
            append.println(sb.toString());
        }
        if (this.zzaGK > 0) {
            printWriter.append(str).append("lastSuspendedCause=");
            int i2 = this.zzaGJ;
            switch (i2) {
                case 1:
                    str3 = "CAUSE_SERVICE_DISCONNECTED";
                    break;
                case 2:
                    str3 = "CAUSE_NETWORK_LOST";
                    break;
                default:
                    str3 = String.valueOf(i2);
                    break;
            }
            printWriter.append(str3);
            PrintWriter append2 = printWriter.append(" lastSuspendedTime=");
            long j2 = this.zzaGK;
            String valueOf2 = String.valueOf(simpleDateFormat.format(new Date(j2)));
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 21);
            sb2.append(j2);
            sb2.append(" ");
            sb2.append(valueOf2);
            append2.println(sb2.toString());
        }
        if (this.zzaGN > 0) {
            printWriter.append(str).append("lastFailedStatus=").append(CommonStatusCodes.getStatusCodeString(this.zzaGM));
            PrintWriter append3 = printWriter.append(" lastFailedTime=");
            long j3 = this.zzaGN;
            String valueOf3 = String.valueOf(simpleDateFormat.format(new Date(j3)));
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf3).length() + 21);
            sb3.append(j3);
            sb3.append(" ");
            sb3.append(valueOf3);
            append3.println(sb3.toString());
        }
    }

    public Account getAccount() {
        return null;
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final Looper getLooper() {
        return this.zzrP;
    }

    public final boolean isConnected() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzaGW == 4;
        }
        return z;
    }

    public final boolean isConnecting() {
        boolean z;
        synchronized (this.mLock) {
            if (this.zzaGW != 2) {
                if (this.zzaGW != 3) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.zzaGM = connectionResult.getErrorCode();
        this.zzaGN = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public final void onConnectionSuspended(int i) {
        this.zzaGJ = i;
        this.zzaGK = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public final void zza(int i, @Nullable Bundle bundle, int i2) {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(7, i2, -1, new zzo(this, i, (Bundle) null)));
    }

    /* access modifiers changed from: protected */
    public void zza(int i, IBinder iBinder, Bundle bundle, int i2) {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(1, i2, -1, new zzn(this, i, iBinder, bundle)));
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void zza(@NonNull T t) {
        this.zzaGL = System.currentTimeMillis();
    }

    @WorkerThread
    public final void zza(zzam zzam, Set<Scope> set) {
        Bundle zzmm = zzmm();
        zzy zzy = new zzy(this.zzaGZ);
        zzy.zzaHy = this.mContext.getPackageName();
        zzy.zzaHB = zzmm;
        if (set != null) {
            zzy.zzaHA = (Scope[]) set.toArray(new Scope[set.size()]);
        }
        if (zzmt()) {
            zzy.zzaHC = getAccount() != null ? getAccount() : new Account("<<default account>>", "com.google");
            if (zzam != null) {
                zzy.zzaHz = zzam.asBinder();
            }
        } else if (zzre()) {
            zzy.zzaHC = getAccount();
        }
        zzy.zzaHD = zzrb();
        try {
            synchronized (this.zzaGQ) {
                if (this.zzaGR != null) {
                    this.zzaGR.zza(new zzk(this, this.zzaHd.get()), zzy);
                } else {
                    Log.w("GmsClient", "mServiceBroker is null, client disconnected");
                }
            }
        } catch (DeadObjectException e) {
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            zzay(1);
        } catch (SecurityException e2) {
            throw e2;
        } catch (RemoteException | RuntimeException e3) {
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e3);
            zza(8, (IBinder) null, (Bundle) null, this.zzaHd.get());
        }
    }

    public void zza(@NonNull zzj zzj) {
        this.zzaGS = (zzj) zzbr.zzb(zzj, (Object) "Connection progress callbacks cannot be null.");
        zza(2, (IInterface) null);
    }

    /* access modifiers changed from: protected */
    public final void zza(@NonNull zzj zzj, int i, @Nullable PendingIntent pendingIntent) {
        this.zzaGS = (zzj) zzbr.zzb(zzj, (Object) "Connection progress callbacks cannot be null.");
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(3, this.zzaHd.get(), i, pendingIntent));
    }

    public final void zzay(int i) {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(6, this.zzaHd.get(), i));
    }

    /* access modifiers changed from: protected */
    @Nullable
    public abstract T zzd(IBinder iBinder);

    /* access modifiers changed from: protected */
    @NonNull
    public abstract String zzda();

    /* access modifiers changed from: protected */
    @NonNull
    public abstract String zzdb();

    public boolean zzmE() {
        return false;
    }

    public Intent zzmF() {
        throw new UnsupportedOperationException("Not a sign in API");
    }

    /* access modifiers changed from: protected */
    public Bundle zzmm() {
        return new Bundle();
    }

    public boolean zzmt() {
        return false;
    }

    public Bundle zzoA() {
        return null;
    }

    public boolean zzpc() {
        return true;
    }

    /* access modifiers changed from: protected */
    public String zzqX() {
        return "com.google.android.gms";
    }

    public final void zzqZ() {
        int isGooglePlayServicesAvailable = this.zzaCH.isGooglePlayServicesAvailable(this.mContext);
        if (isGooglePlayServicesAvailable != 0) {
            zza(1, (IInterface) null);
            zza((zzj) new zzm(this), isGooglePlayServicesAvailable, (PendingIntent) null);
            return;
        }
        zza((zzj) new zzm(this));
    }

    public zzc[] zzrb() {
        return new zzc[0];
    }

    /* access modifiers changed from: protected */
    public final void zzrc() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    public final T zzrd() throws DeadObjectException {
        T t;
        synchronized (this.mLock) {
            if (this.zzaGW != 5) {
                zzrc();
                zzbr.zza(this.zzaGT != null, (Object) "Client is connected but service is null");
                t = this.zzaGT;
            } else {
                throw new DeadObjectException();
            }
        }
        return t;
    }

    public boolean zzre() {
        return false;
    }

    /* access modifiers changed from: protected */
    public Set<Scope> zzrf() {
        return Collections.EMPTY_SET;
    }
}
