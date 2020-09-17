package com.example.practice.network;

import android.content.Context;

import com.example.practice.config.BaseUrl;
import com.example.practice.utils.SharePrefUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Field;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    private Context mContext;

    public TokenInterceptor(Context context) {
        this.mContext = context;
    }

    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        if (checkUrl(request.url().toString())) {
            Request.Builder requestBuilder = request.newBuilder().
                    addHeader("Authorization", "Bearer " +
                            SharePrefUtil.getString(mContext, "token", ""))
                    .method(request.method(), request.body());
            return chain.proceed(requestBuilder.build());
        } else {
            return chain.proceed(request);
        }
    }

    private boolean checkUrl(String url) {
        boolean isChecked = false;
        try {
            Class mClass = Class.forName(BaseUrl.class.getName());
            Field[] fields = mClass.getDeclaredFields();
            for (Field field : fields) {
                if (url.startsWith((String) field.get(mClass))) {
                    isChecked = true;
                    break;
                }
            }
        } catch (IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return isChecked;
    }
}