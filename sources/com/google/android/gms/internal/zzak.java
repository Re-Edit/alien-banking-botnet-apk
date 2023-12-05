package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public final class zzak implements zzan {
    private HttpClient zzaB;

    public zzak(HttpClient httpClient) {
        this.zzaB = httpClient;
    }

    private static void zza(HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase, zzp<?> zzp) throws zza {
        byte[] zzg = zzp.zzg();
        if (zzg != null) {
            httpEntityEnclosingRequestBase.setEntity(new ByteArrayEntity(zzg));
        }
    }

    private static void zza(HttpUriRequest httpUriRequest, Map<String, String> map) {
        for (String next : map.keySet()) {
            httpUriRequest.setHeader(next, map.get(next));
        }
    }

    public final HttpResponse zza(zzp<?> zzp, Map<String, String> map) throws IOException, zza {
        zzal zzal;
        switch (zzp.getMethod()) {
            case -1:
                zzal = new HttpGet(zzp.getUrl());
                break;
            case 0:
                zzal = new HttpGet(zzp.getUrl());
                break;
            case 1:
                zzal = new HttpPost(zzp.getUrl());
                zzal.addHeader("Content-Type", zzp.zzf());
                break;
            case 2:
                zzal = new HttpPut(zzp.getUrl());
                zzal.addHeader("Content-Type", zzp.zzf());
                break;
            case 3:
                zzal = new HttpDelete(zzp.getUrl());
                break;
            case 4:
                zzal = new HttpHead(zzp.getUrl());
                break;
            case 5:
                zzal = new HttpOptions(zzp.getUrl());
                break;
            case 6:
                zzal = new HttpTrace(zzp.getUrl());
                break;
            case 7:
                zzal = new zzal(zzp.getUrl());
                zzal.addHeader("Content-Type", zzp.zzf());
                break;
            default:
                throw new IllegalStateException("Unknown request method.");
        }
        zza((HttpEntityEnclosingRequestBase) zzal, zzp);
        zza((HttpUriRequest) zzal, map);
        zza((HttpUriRequest) zzal, zzp.getHeaders());
        HttpParams params = zzal.getParams();
        int zzi = zzp.zzi();
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, zzi);
        return this.zzaB.execute(zzal);
    }
}
