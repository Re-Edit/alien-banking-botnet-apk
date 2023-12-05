package com.google.android.gms.safetynet;

public class SafeBrowsingThreat {
    public static final int TYPE_POTENTIALLY_HARMFUL_APPLICATION = 4;
    public static final int TYPE_SOCIAL_ENGINEERING = 5;
    private int zzbBO;

    public SafeBrowsingThreat(int i) {
        this.zzbBO = i;
    }

    public int getThreatType() {
        return this.zzbBO;
    }
}
