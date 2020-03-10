package com.wybs.mbti.test;

import com.wybs.mbti.common.util.UrlUtil;
import org.asynchttpclient.*;
import org.asynchttpclient.request.body.multipart.FilePart;
import org.asynchttpclient.request.body.multipart.StringPart;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

/**
 * <p>接口测试</p>
 *
 * <p>Date：2018-04-02</p>
 * 
 * @author Mumus
 */
public abstract class AbstractApiTest {
    private static final Logger logger = LoggerFactory.getLogger(AbstractApiTest.class);

    protected static AsyncHttpClient client;

    // protected String host = "http://114.55.115.107:8068";

    protected String host = "http://localhost:8760";

    @BeforeClass
    public static void createAsyncHttpClient() {
        DefaultAsyncHttpClientConfig.Builder builder = new DefaultAsyncHttpClientConfig.Builder();
        builder.setConnectTimeout(60000);
        builder.setRequestTimeout(60000);
        builder.setReadTimeout(60000);
        builder.setMaxConnectionsPerHost(5000);
        builder.setMaxConnections(5000);
        client = new DefaultAsyncHttpClient(builder.build());
    }

    @AfterClass
    public static void release() {
        try {
            client.close();
        } catch (IOException ignore) {
            // ignore
        }
    }

    protected Map<String, Object> getPublicParams() {
        Map<String, Object> params = new HashMap<>(8);

        return params;
    }

    protected String doGet(String url) throws InterruptedException, ExecutionException {
        BoundRequestBuilder reqBuilder = client.prepareGet(url);
        reqBuilder.setHeader("Content-Type", "application/json");
        Future<Response> f = reqBuilder.execute();

        Response resp = f.get();
        String body = resp.getResponseBody(Charset.forName("UTF-8"));

        logger.debug("doGet() - http status={}, url={}, body={}", resp.getStatusCode(), url, body);

        return body;
    }

    protected String doPost(final String url, final Map<String, Object> params) throws InterruptedException, ExecutionException {
        BoundRequestBuilder reqBuilder = client.preparePost(url);
        reqBuilder.setCharset(Charset.forName("UTF-8"));

        params.entrySet().stream().filter(entry -> entry.getValue() != null)
            .forEach(entry -> reqBuilder.addFormParam(entry.getKey(), String.valueOf(entry.getValue())));
        Future<Response> f = reqBuilder.execute();

        Response resp = f.get();
        String body = resp.getResponseBody(Charset.forName("UTF-8"));

        logger.debug("doPost() - http status={}, url={}, body={}", resp.getStatusCode(), UrlUtil.buildUrl(url, params), body);

        return body;
    }

    protected String doPostUpload(final String url, final Map<String, Object> params) throws InterruptedException, ExecutionException {
        BoundRequestBuilder reqBuilder = client.preparePost(url);
        reqBuilder.setCharset(Charset.forName("UTF-8"));

        params.entrySet().stream().filter(entry -> entry.getValue() != null).forEach(entry -> {
            Object val = entry.getValue();
            if (val instanceof File) {
                reqBuilder.addBodyPart(new FilePart(entry.getKey(), (File)val));
            } else {
                reqBuilder.addBodyPart(new StringPart(entry.getKey(), String.valueOf(val)));
            }
        });

        Future<Response> f = reqBuilder.execute();

        Response resp = f.get();
        String body = resp.getResponseBody(Charset.forName("UTF-8"));

        logger.debug("doPost() - http status={}, url={}, body={}", resp.getStatusCode(), UrlUtil.buildUrl(url, params), body);

        return body;
    }

    protected String doPut(final String url, final Map<String, Object> params) throws InterruptedException, ExecutionException {
        BoundRequestBuilder reqBuilder = client.preparePut(url);
        reqBuilder.setCharset(Charset.forName("UTF-8"));

        params.entrySet().stream().filter(entry -> entry.getValue() != null)
            .forEach(entry -> reqBuilder.addFormParam(entry.getKey(), String.valueOf(entry.getValue())));
        Future<Response> f = reqBuilder.execute();

        Response resp = f.get();
        String body = resp.getResponseBody(Charset.forName("UTF-8"));

        logger.debug("doPut() - http status={}, url={}, body={}", resp.getStatusCode(), UrlUtil.buildUrl(url, params), body);

        return body;
    }

    protected String doDelete(String url) throws InterruptedException, ExecutionException {
        BoundRequestBuilder reqBuilder = client.prepareDelete(url);
        Future<Response> f = reqBuilder.execute();

        Response resp = f.get();
        String body = resp.getResponseBody(Charset.forName("UTF-8"));

        logger.debug("doDelete() - http status={}, url={}, body={}", resp.getStatusCode(), url, body);

        return body;
    }

    @SuppressWarnings("unused")
    private String doGet1(String url) {
        try {
            BoundRequestBuilder reqBuilder = client.prepareGet(url);
            reqBuilder.setHeader("Content-Type", "application/json");
            Future<Response> f = reqBuilder.execute();

            Response resp = f.get();
            String body = resp.getResponseBody(Charset.forName("UTF-8"));

            logger.debug("doGet() - http status={}, url={}, body={}", resp.getStatusCode(), url, body);

            return body;
        } catch (Exception e) {
            Throwable cause = e.getCause() == null ? e : e.getCause();
            if (cause instanceof ConnectException || cause instanceof UnknownHostException) {
                logger.error("testPostPayApply() +++++++++++++ 网络错误", e);
            } else if (cause instanceof TimeoutException) {
                logger.error("testPostPayApply() ------------- 响应超时", e);
            }
            logger.error("testPostPayApply() - error={}", e.getMessage(), e.getCause());
            return null;
        }
    }
}
