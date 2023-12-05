package com.google.android.gms.internal;

import java.io.IOException;

public final class aie extends IOException {
    public aie(String str) {
        super(str);
    }

    static aie zzMg() {
        return new aie("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either than the input has been truncated or that an embedded message misreported its own length.");
    }

    static aie zzMh() {
        return new aie("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    static aie zzMi() {
        return new aie("CodedInputStream encountered a malformed varint.");
    }

    static aie zzMj() {
        return new aie("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
    }
}
