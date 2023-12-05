package com.google.android.gms.common.images;

public final class Size {
    private final int zzrZ;
    private final int zzsa;

    public Size(int i, int i2) {
        this.zzrZ = i;
        this.zzsa = i2;
    }

    public static Size parseSize(String str) throws NumberFormatException {
        if (str != null) {
            int indexOf = str.indexOf(42);
            if (indexOf < 0) {
                indexOf = str.indexOf(120);
            }
            if (indexOf >= 0) {
                try {
                    return new Size(Integer.parseInt(str.substring(0, indexOf)), Integer.parseInt(str.substring(indexOf + 1)));
                } catch (NumberFormatException unused) {
                    throw zzcy(str);
                }
            } else {
                throw zzcy(str);
            }
        } else {
            throw new IllegalArgumentException("string must not be null");
        }
    }

    private static NumberFormatException zzcy(String str) {
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 16);
        sb.append("Invalid Size: \"");
        sb.append(str);
        sb.append("\"");
        throw new NumberFormatException(sb.toString());
    }

    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof Size) {
            Size size = (Size) obj;
            return this.zzrZ == size.zzrZ && this.zzsa == size.zzsa;
        }
    }

    public final int getHeight() {
        return this.zzsa;
    }

    public final int getWidth() {
        return this.zzrZ;
    }

    public final int hashCode() {
        int i = this.zzsa;
        int i2 = this.zzrZ;
        return i ^ ((i2 >>> 16) | (i2 << 16));
    }

    public final String toString() {
        int i = this.zzrZ;
        int i2 = this.zzsa;
        StringBuilder sb = new StringBuilder(23);
        sb.append(i);
        sb.append("x");
        sb.append(i2);
        return sb.toString();
    }
}
