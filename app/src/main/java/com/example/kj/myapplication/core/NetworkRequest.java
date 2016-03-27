package com.example.kj.myapplication.core;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class NetworkRequest {

    final String LOG_TAG = getClass().getSimpleName();
    static final String COOKIES_HEADER = "Set-Cookie";
    final static private CookieManager msCookieManager = new CookieManager();

    public NetworkRequest() {}

    public String makePostRequest(URL url, JSONObject data) {
        return makeRequest(url, "POST", data);
    }

    public String makeGetRequest(URL url) {
        return makeGetRequest(url, null);
    }

    public String makeGetRequest(URL url, JSONObject data) {
        return makeRequest(url, "GET", data);
    }

    private String makeRequest(URL url, String method, JSONObject data) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            connection = getConnection(url, method, data);

            List<String> cookiesHeader = connection.getHeaderFields().get(COOKIES_HEADER);
            if(cookiesHeader != null) {
                for (String cookie : cookiesHeader) {
                    msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                }
            }

            InputStream inputStream = connection.getInputStream();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
                buffer.append("\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            return buffer.toString();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally{
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
    }

    private HttpURLConnection getConnection(URL url, String method, JSONObject data) throws Exception {
        if (!method.equals("POST") && !method.equals("GET")) {
            throw new Exception("Unsupportable method");

        }
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);

        synchronized (msCookieManager) {
            if(msCookieManager.getCookieStore().getCookies().size() > 0) {
                connection.setRequestProperty("Cookie",
                        TextUtils.join(";", msCookieManager.getCookieStore().getCookies()));
            }
        }

        switch (method) {
            case "POST":
                String message = data.toString();

                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setFixedLengthStreamingMode(message.getBytes().length);
                connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
                connection.connect();
                //setup send
                OutputStream os = new BufferedOutputStream(connection.getOutputStream());
                os.write(message.getBytes());
                //clean up
                os.flush();

                break;
            case "GET":
                break;
        }

        connection.connect();

        synchronized (msCookieManager) {
            List<String> cookiesHeader = connection.getHeaderFields().get(COOKIES_HEADER);
            if(cookiesHeader != null) {
                for (String cookie : cookiesHeader) {
                    msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                }
            }
        }

        return connection;
    }

}