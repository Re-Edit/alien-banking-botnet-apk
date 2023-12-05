package com.google.android.gms.ads.identifier;

import android.util.Log;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

final class zza extends Thread {
    private /* synthetic */ String zzsG;

    zza(AdvertisingIdClient advertisingIdClient, String str) {
        this.zzsG = str;
    }

    public final void run() {
        String str;
        String valueOf;
        StringBuilder sb;
        String str2;
        Exception exc;
        HttpURLConnection httpURLConnection;
        new zzb();
        String str3 = this.zzsG;
        try {
            httpURLConnection = (HttpURLConnection) new URL(str3).openConnection();
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode < 200 || responseCode >= 300) {
                StringBuilder sb2 = new StringBuilder(String.valueOf(str3).length() + 65);
                sb2.append("Received non-success response code ");
                sb2.append(responseCode);
                sb2.append(" from pinging URL: ");
                sb2.append(str3);
                Log.w("HttpUrlPinger", sb2.toString());
            }
            httpURLConnection.disconnect();
        } catch (IndexOutOfBoundsException e) {
            str = "HttpUrlPinger";
            valueOf = String.valueOf(e.getMessage());
            sb = new StringBuilder(String.valueOf(str3).length() + 32 + String.valueOf(valueOf).length());
            str2 = "Error while parsing ping URL: ";
            exc = e;
            sb.append(str2);
            sb.append(str3);
            sb.append(". ");
            sb.append(valueOf);
            Log.w(str, sb.toString(), exc);
        } catch (IOException | RuntimeException e2) {
            str = "HttpUrlPinger";
            valueOf = String.valueOf(e2.getMessage());
            sb = new StringBuilder(String.valueOf(str3).length() + 27 + String.valueOf(valueOf).length());
            str2 = "Error while pinging URL: ";
            exc = e2;
            sb.append(str2);
            sb.append(str3);
            sb.append(". ");
            sb.append(valueOf);
            Log.w(str, sb.toString(), exc);
        } catch (Throwable th) {
            httpURLConnection.disconnect();
            throw th;
        }
    }
}
