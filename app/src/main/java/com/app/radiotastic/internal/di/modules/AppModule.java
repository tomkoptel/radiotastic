package com.app.radiotastic.internal.di.modules;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.app.radiotastic.R;
import com.app.radiotastic.data.db.AppSQLiteOpenHelper;
import com.app.radiotastic.data.entity.DirbleStation;
import com.app.radiotastic.data.entity.DirbleStationStorIOSQLiteDeleteResolver;
import com.app.radiotastic.data.entity.DirbleStationStorIOSQLiteGetResolver;
import com.app.radiotastic.data.entity.DirbleStationStorIOSQLitePutResolver;
import com.app.radiotastic.data.net.DirbleRestApi;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;

/**
 * @author Tom Koptel
 */
@Module
public final class AppModule {
    private final Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    public DirbleRestApi provideRestApi(Context context) {
        final String token = context.getString(R.string.dirble_api_key);
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

    @Provides
    @Singleton
    public SQLiteOpenHelper providesSqliteOpenHelper(Context context) {
        return AppSQLiteOpenHelper.newInstance(context);
    }

    @Provides
    @Singleton
    public StorIOSQLite provideStoreIO(SQLiteOpenHelper sqLiteOpenHelper) {
        SQLiteTypeMapping<DirbleStation> typeMapping = SQLiteTypeMapping.<DirbleStation>builder()
                .putResolver(new DirbleStationStorIOSQLitePutResolver())
                .getResolver(new DirbleStationStorIOSQLiteGetResolver())
                .deleteResolver(new DirbleStationStorIOSQLiteDeleteResolver())
                .build();

        return DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(sqLiteOpenHelper)
                .addTypeMapping(DirbleStation.class, typeMapping)
                .build();
    }
}
