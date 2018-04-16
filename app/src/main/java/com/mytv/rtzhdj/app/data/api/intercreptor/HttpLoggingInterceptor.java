package com.mytv.rtzhdj.app.data.api.intercreptor;

import android.text.TextUtils;
import android.util.Log;

import com.mytv.rtzhdj.app.utils.Logging;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 日志拦截操作
 * Created by itbird on 2017/3/8
 */

public class HttpLoggingInterceptor implements Interceptor {
    public static final String TAG = HttpLoggingInterceptor.class.getSimpleName();

    // 是否允许使用Okhttp缓存策略
    // 允许使用时，如果服务端设置相应的缓存策略那么遵从服务端的不做修改，否则判断接口请求时是否设置cacheControl
    // 如果请求接口中未设置cacheControl，则统一设置为一分钟
    public boolean mIsAllowCache = false;

    public HttpLoggingInterceptor(boolean isAllowCache) {
        mIsAllowCache = isAllowCache;
    }

    public HttpLoggingInterceptor() {
        mIsAllowCache = false;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (Logging.isDebugLogging()) {
            //the request url
            String url = request.url().toString();
            //the request method
            String method = request.method();
            long t1 = System.nanoTime();
            Log.d(TAG, String.format(Locale.getDefault(), "Sending %s request [url = %s]", method, url));
            //the request body
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                StringBuilder sb = new StringBuilder("Request Body [");
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                Charset charset = Charset.forName("UTF-8");
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(charset);
                }
                if (isPlaintext(buffer)) {
                    sb.append(buffer.readString(charset));
                    sb.append(" (Content-Type = ").append(contentType.toString()).append(",")
                            .append(requestBody.contentLength()).append("-byte body)");
                } else {
                    sb.append(" (Content-Type = ").append(contentType.toString())
                            .append(",binary ").append(requestBody.contentLength()).append("-byte body omitted)");
                }
                sb.append("]");
                Log.d(TAG, String.format(Locale.getDefault(), "%s %s", method, sb.toString()));
            }
            long t2 = System.nanoTime();
            //the response time
            Log.d(TAG, String.format(Locale.getDefault(), "Received response for [url = %s] in %.1fms", url, (t2 - t1) / 1e6d));

            //the response state
            Log.d(TAG, String.format(Locale.CHINA, "Received response is %s ,message[%s],code[%d]", response.isSuccessful() ? "success" : "fail", response.message(), response.code()));

            //the response data
            ResponseBody body = response.body();

            BufferedSource source = body.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = Charset.defaultCharset();
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            String bodyString = buffer.clone().readString(charset);
            Log.d(TAG, String.format("Received response json string [%s]", bodyString));
        }

        if (mIsAllowCache) {
            String serverCache = response.header("Cache-Control");
            // 如果服务端设置相应的缓存策略那么遵从服务端的不做修改
            if (TextUtils.isEmpty(serverCache)) {
                String cacheControl = request.cacheControl().toString();
                if (TextUtils.isEmpty(cacheControl)) {
                    // 如果请求接口中未设置cacheControl，则统一设置为一分钟
                    int maxAge = 1 * 60; // 在线缓存在1分钟内可读取 单位:秒
                    return response.newBuilder()
                            .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                    return response.newBuilder()
                            .addHeader("Cache-Control", cacheControl)
                            .removeHeader("Pragma")
                            .build();
                }
            }
        }

        return response;
    }

    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

}
