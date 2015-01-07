package com.to8to.clickstream.network;

import android.text.TextUtils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by moon.zhong on 2014/12/25.
 */
public class ClickHttp implements IHttpStack {
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    public ClickHttp() {
    }

    @Override
    public HttpResponse performRequest(AbsRequest absRequest) throws IOException {
        String url = absRequest.getUrl();
        Map<String, String> map = new HashMap<>();
        map.putAll(absRequest.getHeader());
        if (TextUtils.isEmpty(url)) {
            new Exception("url not null");
        }
        URL urlParas = new URL(url);
        HttpURLConnection connection = openUrlConnection(urlParas, absRequest) ;
        for(String headerName : map.keySet()){
            connection.setRequestProperty(headerName, map.get(headerName));
        }
        setHttpMethod(connection, absRequest);
        ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
        int responseCode = connection.getResponseCode();
        if (responseCode == -1) {
            // -1 is returned by getResponseCode() if the response code could not be retrieved.
            // Signal to the caller that something was wrong with the connection.
            throw new IOException("Could not retrieve response code from HttpUrlConnection.");
        }
        StatusLine responseStatus = new BasicStatusLine(protocolVersion,
                connection.getResponseCode(), connection.getResponseMessage());
        BasicHttpResponse response = new BasicHttpResponse(responseStatus);
        response.setEntity(entityFromConnection(connection));
        for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
            if (header.getKey() != null) {
                Header h = new BasicHeader(header.getKey(), header.getValue().get(0));
                response.addHeader(h);
            }
        }
        return response;
    }

    private HttpURLConnection openUrlConnection(URL url, AbsRequest request) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int timeOutMs = request.getTimeOut() ;
        connection.setConnectTimeout(timeOutMs);
        connection.setReadTimeout(timeOutMs);
        connection.setUseCaches(false);
        connection.setDoInput(true);
        return connection;
    }

    private void setHttpMethod(HttpURLConnection connection,AbsRequest absRequest)throws IOException{
        switch (absRequest.getMethod()){
            case AbsRequest.Method.GET:
                connection.setRequestMethod("GET");
                addBodyIfExists(connection,absRequest) ;
                break ;
            case AbsRequest.Method.POST:
                connection.setRequestMethod("POST");
                addBodyIfExists(connection,absRequest) ;
                break ;
        }
    }

    /**
     * Initializes an {@link org.apache.http.HttpEntity} from the given {@link java.net.HttpURLConnection}.
     * @param connection
     * @return an HttpEntity populated with data from <code>connection</code>.
     */
    private static HttpEntity entityFromConnection(HttpURLConnection connection) {
        BasicHttpEntity entity = new BasicHttpEntity();
        InputStream inputStream;
        try {
            inputStream = connection.getInputStream();
        } catch (IOException ioe) {
            inputStream = connection.getErrorStream();
        }
        entity.setContent(inputStream);
        entity.setContentLength(connection.getContentLength());
        entity.setContentEncoding(connection.getContentEncoding());
        entity.setContentType(connection.getContentType());
        return entity;
    }

    private static void addBodyIfExists(HttpURLConnection connection,AbsRequest<?> request)
            throws IOException{
        byte[] body = request.getBody();
        if (body != null) {
            connection.setDoOutput(true);
            connection.addRequestProperty(HEADER_CONTENT_TYPE, request.getBodyContentType());
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(body);
            out.close();
        }
    }
}
