package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbh;
import java.util.Arrays;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class WebImage extends zza {
    public static final Parcelable.Creator<WebImage> CREATOR = new zze();
    private int zzakw;
    private final Uri zzauS;
    private final int zzrZ;
    private final int zzsa;

    WebImage(int i, Uri uri, int i2, int i3) {
        this.zzakw = i;
        this.zzauS = uri;
        this.zzrZ = i2;
        this.zzsa = i3;
    }

    public WebImage(Uri uri) throws IllegalArgumentException {
        this(uri, 0, 0);
    }

    public WebImage(Uri uri, int i, int i2) throws IllegalArgumentException {
        this(1, uri, i, i2);
        if (uri == null) {
            throw new IllegalArgumentException("url cannot be null");
        } else if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("width and height must not be negative");
        }
    }

    public WebImage(JSONObject jSONObject) throws IllegalArgumentException {
        this(zzp(jSONObject), jSONObject.optInt("width", 0), jSONObject.optInt("height", 0));
    }

    private static Uri zzp(JSONObject jSONObject) {
        if (jSONObject.has("url")) {
            try {
                return Uri.parse(jSONObject.getString("url"));
            } catch (JSONException unused) {
            }
        }
        return null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof WebImage)) {
            WebImage webImage = (WebImage) obj;
            return zzbh.equal(this.zzauS, webImage.zzauS) && this.zzrZ == webImage.zzrZ && this.zzsa == webImage.zzsa;
        }
    }

    public final int getHeight() {
        return this.zzsa;
    }

    public final Uri getUrl() {
        return this.zzauS;
    }

    public final int getWidth() {
        return this.zzrZ;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzauS, Integer.valueOf(this.zzrZ), Integer.valueOf(this.zzsa)});
    }

    public final JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("url", this.zzauS.toString());
            jSONObject.put("width", this.zzrZ);
            jSONObject.put("height", this.zzsa);
        } catch (JSONException unused) {
        }
        return jSONObject;
    }

    public final String toString() {
        return String.format(Locale.US, "Image %dx%d %s", new Object[]{Integer.valueOf(this.zzrZ), Integer.valueOf(this.zzsa), this.zzauS.toString()});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        zzd.zza(parcel, 2, (Parcelable) getUrl(), i, false);
        zzd.zzc(parcel, 3, getWidth());
        zzd.zzc(parcel, 4, getHeight());
        zzd.zzI(parcel, zze);
    }
}
