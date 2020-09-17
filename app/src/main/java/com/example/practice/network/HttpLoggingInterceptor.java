package com.example.practice.network;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSource;

/**
 * An OkHttp interceptor which logs request and response information. Can be applied as an
 * <p>
 * The format of the logs created by this class should not be considered stable and may change
 * slightly between releases. If you need a stable logging format, use your own interceptor.
 */
public final class HttpLoggingInterceptor implements Interceptor {
    private static final Charset UTF8 = StandardCharsets.UTF_8;

    public enum Level {
        /**
         * No logs.
         */
        NONE,
        /**
         * Logs request and response lines.
         * <p>
         * Example:
         * <pre>{@code
         * --> POST /greeting HTTP/1.1 (3-byte body)
         * <p>
         * <-- HTTP/1.1 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         * <p>
         * Example:
         * <pre>{@code
         * --> POST /greeting HTTP/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         * <p>
         * <-- HTTP/1.1 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * }</pre>
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         * <p>
         * Example:
         * <pre>{@code
         * --> POST /greeting HTTP/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * <p>
         * Hi?
         * --> END GET
         * <p>
         * <-- HTTP/1.1 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <p>
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
        BODY
    }

    public interface Logger {
        void log(String message);

        /**
         * A {@link Logger} defaults output appropriate for the current platform.
         */
        Logger DEFAULT = new Logger() {
            @Override
            public void log(String message) {
                Platform.get().log(message,Platform.WARN,null);
            }
        };
    }

    public HttpLoggingInterceptor() {
        this(Logger.DEFAULT);
    }

    public HttpLoggingInterceptor(Logger logger) {
        this.logger = logger;
    }

    private final Logger logger;

    private volatile Level level = Level.NONE;

    /**
     * Change the level at which this interceptor logs.
     */
    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Level level = this.level;

        Request request = chain.request();
        if (level == Level.NONE) {
            return chain.proceed(request);
        }

        boolean logBody = level == Level.BODY;
        boolean logHeaders = logBody || level == Level.HEADERS;

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        String requestStartMessage =
                "--> " + request.method() + ' ' + requestPath(request.url()) + ' ' + protocol(protocol);
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
        }
        logger.log(requestStartMessage);

        if (logHeaders) {
            Headers headers = request.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                logger.log(headers.name(i) + ": " + headers.value(i));
            }

            String endMessage = "--> END " + request.method();
            if (logBody && hasRequestBody) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    contentType.charset(UTF8);
                }

                logger.log("");
                logger.log(buffer.readString(charset));

                endMessage += " (" + requestBody.contentLength() + "-byte body)";
            }
            logger.log(endMessage);
        }

        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        logger.log("<-- " + protocol(response.protocol()) + ' ' + response.code() + ' '
                + response.message() + " (" + tookMs + "ms"
                + (!logHeaders ? ", " + responseBody.contentLength() + "-byte body" : "") + ')');

        if (logHeaders) {
            Headers headers = response.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                logger.log(headers.name(i) + ": " + headers.value(i));
            }

            String endMessage = "<-- END HTTP";
            if (logBody) {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.getBuffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (responseBody.contentLength() != 0) {
                    logger.log("");
                    logger.log(buffer.clone().readString(charset));
                }

                endMessage += " (" + buffer.size() + "-byte body)";
            }
            logger.log(endMessage);
        }

        return response;
    }

    private static String protocol(Protocol protocol) {
        return protocol == Protocol.HTTP_1_0 ? "HTTP/1.0" : "HTTP/1.1";
    }

    private static String requestPath(HttpUrl url) {
        String path = url.encodedPath();
        String query = url.encodedQuery();
        return query != null ? (path + '?' + query) : path;
    }
}