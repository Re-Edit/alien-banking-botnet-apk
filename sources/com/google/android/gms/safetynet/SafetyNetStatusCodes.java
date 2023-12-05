package com.google.android.gms.safetynet;

import com.google.android.gms.common.api.CommonStatusCodes;

public final class SafetyNetStatusCodes extends CommonStatusCodes {
    public static final int INVALID_ADMIN_APPLICATION = 12011;
    public static final int OS_VERSION_NOT_SUPPORTED = 12010;
    public static final int RECAPTCHA_INVALID_KEYTYPE = 12008;
    public static final int RECAPTCHA_INVALID_PACKAGE_NAME = 12013;
    public static final int RECAPTCHA_INVALID_SITEKEY = 12007;
    public static final int SAFE_BROWSING_API_NOT_AVAILABLE = 12002;
    public static final int SAFE_BROWSING_API_NOT_INITIALIZED = 12009;
    public static final int SAFE_BROWSING_MISSING_API_KEY = 12001;
    public static final int SAFE_BROWSING_UNSUPPORTED_THREAT_TYPES = 12000;
    public static final int STRICT_MODE_VERIFY_APPS_NOT_AVAILABLE = 12012;
    public static final int UNSUPPORTED_SDK_VERSION = 12006;
    public static final int VERIFY_APPS_INTERNAL_ERROR = 12004;
    public static final int VERIFY_APPS_NOT_AVAILABLE = 12003;
    public static final int VERIFY_APPS_NOT_ENABLED = 12005;

    private SafetyNetStatusCodes() {
    }
}
