package com.app.radiotastic.data.net;

import android.net.Uri;

import com.app.radiotastic.data.entity.DirbleStation;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import retrofit.Retrofit;
import retrofit.http.GET;

/**
 * @author Tom Koptel
 */
public interface DirbleRestApi {
    @GET("stations")
    rx.Observable<List<DirbleStation>> fetchAllStations();

    class Factory {
        public static DirbleRestApi create(final String token) {
            OkHttpClient client = new OkHttpClient();
            client.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Uri uri = Uri.parse(request.url().toString())
                            .buildUpon()
                            .appendQueryParameter("token", token)
                            .build();
                    Request resultRequest = request.newBuilder()
                            .url(uri.toString())
                            .build();
                    return chain.proceed(resultRequest);
                }
            });
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.dirble.com/v2/")
                    .client(client)
                    .build();
            return retrofit.create(DirbleRestApi.class);
        }
    }
}
