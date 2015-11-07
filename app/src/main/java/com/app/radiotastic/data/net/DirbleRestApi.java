package com.app.radiotastic.data.net;

import com.app.radiotastic.data.entity.DirbleStation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
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
                    HttpUrl tokenUrl = request.httpUrl().newBuilder()
                            .addQueryParameter("token", token)
                            .build();
                    Request resultRequest = request.newBuilder()
                            .url(tokenUrl)
                            .build();
                    return chain.proceed(resultRequest);
                }
            });
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            GsonConverterFactory converterFactory = GsonConverterFactory.create(gson);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.dirble.com/v2/")
                    .client(client)
                    .addConverterFactory(converterFactory)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(DirbleRestApi.class);
        }
    }
}
