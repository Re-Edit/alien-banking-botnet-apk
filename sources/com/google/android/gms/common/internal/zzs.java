package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.R;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.util.zzk;
import com.google.android.gms.internal.zzbim;

public final class zzs {
    private static final SimpleArrayMap<String, String> zzaHq = new SimpleArrayMap<>();

    private static String zzaB(Context context) {
        String packageName = context.getPackageName();
        try {
            return zzbim.zzaP(context).zzcN(packageName).toString();
        } catch (PackageManager.NameNotFoundException | NullPointerException unused) {
            String str = context.getApplicationInfo().name;
            return TextUtils.isEmpty(str) ? packageName : str;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006b, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0024, code lost:
        android.util.Log.e(r3, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0027, code lost:
        return null;
     */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String zzg(android.content.Context r3, int r4) {
        /*
            android.content.res.Resources r0 = r3.getResources()
            r1 = 20
            if (r4 == r1) goto L_0x0081
            r1 = 0
            switch(r4) {
                case 1: goto L_0x007a;
                case 2: goto L_0x0073;
                case 3: goto L_0x006c;
                case 4: goto L_0x006b;
                case 5: goto L_0x005d;
                case 6: goto L_0x006b;
                case 7: goto L_0x004f;
                case 8: goto L_0x004a;
                case 9: goto L_0x0045;
                case 10: goto L_0x0040;
                case 11: goto L_0x003b;
                default: goto L_0x000c;
            }
        L_0x000c:
            switch(r4) {
                case 16: goto L_0x0036;
                case 17: goto L_0x0028;
                case 18: goto L_0x006b;
                default: goto L_0x000f;
            }
        L_0x000f:
            java.lang.String r3 = "GoogleApiAvailability"
            r0 = 33
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r0)
            java.lang.String r0 = "Unexpected error code "
            r2.append(r0)
            r2.append(r4)
            java.lang.String r4 = r2.toString()
        L_0x0024:
            android.util.Log.e(r3, r4)
            return r1
        L_0x0028:
            java.lang.String r4 = "GoogleApiAvailability"
            java.lang.String r0 = "The specified account could not be signed in."
            android.util.Log.e(r4, r0)
            java.lang.String r4 = "common_google_play_services_sign_in_failed_title"
            java.lang.String r3 = zzz(r3, r4)
            return r3
        L_0x0036:
            java.lang.String r3 = "GoogleApiAvailability"
            java.lang.String r4 = "One of the API components you attempted to connect to is not available."
            goto L_0x0024
        L_0x003b:
            java.lang.String r3 = "GoogleApiAvailability"
            java.lang.String r4 = "The application is not licensed to the user."
            goto L_0x0024
        L_0x0040:
            java.lang.String r3 = "GoogleApiAvailability"
            java.lang.String r4 = "Developer error occurred. Please see logs for detailed information"
            goto L_0x0024
        L_0x0045:
            java.lang.String r3 = "GoogleApiAvailability"
            java.lang.String r4 = "Google Play services is invalid. Cannot recover."
            goto L_0x0024
        L_0x004a:
            java.lang.String r3 = "GoogleApiAvailability"
            java.lang.String r4 = "Internal error occurred. Please see logs for detailed information"
            goto L_0x0024
        L_0x004f:
            java.lang.String r4 = "GoogleApiAvailability"
            java.lang.String r0 = "Network error occurred. Please retry request later."
            android.util.Log.e(r4, r0)
            java.lang.String r4 = "common_google_play_services_network_error_title"
            java.lang.String r3 = zzz(r3, r4)
            return r3
        L_0x005d:
            java.lang.String r4 = "GoogleApiAvailability"
            java.lang.String r0 = "An invalid account was specified when connecting. Please provide a valid account."
            android.util.Log.e(r4, r0)
            java.lang.String r4 = "common_google_play_services_invalid_account_title"
            java.lang.String r3 = zzz(r3, r4)
            return r3
        L_0x006b:
            return r1
        L_0x006c:
            int r3 = com.google.android.gms.R.string.common_google_play_services_enable_title
            java.lang.String r3 = r0.getString(r3)
            return r3
        L_0x0073:
            int r3 = com.google.android.gms.R.string.common_google_play_services_update_title
            java.lang.String r3 = r0.getString(r3)
            return r3
        L_0x007a:
            int r3 = com.google.android.gms.R.string.common_google_play_services_install_title
            java.lang.String r3 = r0.getString(r3)
            return r3
        L_0x0081:
            java.lang.String r4 = "GoogleApiAvailability"
            java.lang.String r0 = "The current user profile is restricted and could not use authenticated features."
            android.util.Log.e(r4, r0)
            java.lang.String r4 = "common_google_play_services_restricted_profile_title"
            java.lang.String r3 = zzz(r3, r4)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzs.zzg(android.content.Context, int):java.lang.String");
    }

    @NonNull
    public static String zzh(Context context, int i) {
        String zzz = i == 6 ? zzz(context, "common_google_play_services_resolution_required_title") : zzg(context, i);
        return zzz == null ? context.getResources().getString(R.string.common_google_play_services_notification_ticker) : zzz;
    }

    @NonNull
    public static String zzi(Context context, int i) {
        Resources resources = context.getResources();
        String zzaB = zzaB(context);
        if (i == 5) {
            return zzl(context, "common_google_play_services_invalid_account_text", zzaB);
        }
        if (i == 7) {
            return zzl(context, "common_google_play_services_network_error_text", zzaB);
        }
        if (i == 9) {
            return resources.getString(R.string.common_google_play_services_unsupported_text, new Object[]{zzaB});
        } else if (i == 20) {
            return zzl(context, "common_google_play_services_restricted_profile_text", zzaB);
        } else {
            switch (i) {
                case 1:
                    return resources.getString(R.string.common_google_play_services_install_text, new Object[]{zzaB});
                case 2:
                    if (zzk.zzaH(context)) {
                        return resources.getString(R.string.common_google_play_services_wear_update_text);
                    }
                    return resources.getString(R.string.common_google_play_services_update_text, new Object[]{zzaB});
                case 3:
                    return resources.getString(R.string.common_google_play_services_enable_text, new Object[]{zzaB});
                default:
                    switch (i) {
                        case 16:
                            return zzl(context, "common_google_play_services_api_unavailable_text", zzaB);
                        case 17:
                            return zzl(context, "common_google_play_services_sign_in_failed_text", zzaB);
                        case 18:
                            return resources.getString(R.string.common_google_play_services_updating_text, new Object[]{zzaB});
                        default:
                            return resources.getString(R.string.common_google_play_services_unknown_issue, new Object[]{zzaB});
                    }
            }
        }
    }

    @NonNull
    public static String zzj(Context context, int i) {
        return i == 6 ? zzl(context, "common_google_play_services_resolution_required_text", zzaB(context)) : zzi(context, i);
    }

    @NonNull
    public static String zzk(Context context, int i) {
        int i2;
        Resources resources = context.getResources();
        switch (i) {
            case 1:
                i2 = R.string.common_google_play_services_install_button;
                break;
            case 2:
                i2 = R.string.common_google_play_services_update_button;
                break;
            case 3:
                i2 = R.string.common_google_play_services_enable_button;
                break;
            default:
                i2 = 17039370;
                break;
        }
        return resources.getString(i2);
    }

    private static String zzl(Context context, String str, String str2) {
        Resources resources = context.getResources();
        String zzz = zzz(context, str);
        if (zzz == null) {
            zzz = resources.getString(R.string.common_google_play_services_unknown_issue);
        }
        return String.format(resources.getConfiguration().locale, zzz, new Object[]{str2});
    }

    @Nullable
    private static String zzz(Context context, String str) {
        synchronized (zzaHq) {
            String str2 = zzaHq.get(str);
            if (str2 != null) {
                return str2;
            }
            Resources remoteResource = GooglePlayServicesUtil.getRemoteResource(context);
            if (remoteResource == null) {
                return null;
            }
            int identifier = remoteResource.getIdentifier(str, "string", "com.google.android.gms");
            if (identifier == 0) {
                String valueOf = String.valueOf(str);
                Log.w("GoogleApiAvailability", valueOf.length() != 0 ? "Missing resource: ".concat(valueOf) : new String("Missing resource: "));
                return null;
            }
            String string = remoteResource.getString(identifier);
            if (TextUtils.isEmpty(string)) {
                String valueOf2 = String.valueOf(str);
                Log.w("GoogleApiAvailability", valueOf2.length() != 0 ? "Got empty resource: ".concat(valueOf2) : new String("Got empty resource: "));
                return null;
            }
            zzaHq.put(str, string);
            return string;
        }
    }
}
