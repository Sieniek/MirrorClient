package com.jappka.mirrorclient.widget;

import android.util.Pair;

import com.google.api.client.util.IOUtils;
import com.google.common.io.CharStreams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by psienkiewicz on 04/01/16.
 * Here are created calls to perform actions on mirror with API
 */

public class CallAPI {

    /**
     * Urls to use while calling API
     */
    private static final String SERVER_URL = "https://192.168.1.13:8000";
    private static final String WIDGET_ENDPOINT_URL = SERVER_URL + "/api/widgets";

    // always verify the host - don't check for certificate
    // TODO: fix checking for certificate
    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    // always verify the host - don't check for certificate
    // TODO: fix checking for certificate
    private static void trustAll() {
        TrustManager[] trustEverythingTrustManager = new TrustManager[]{
                new X509TrustManager() {

                    public void checkClientTrusted(X509Certificate[] chain,
                                                   String authType) throws CertificateException {
                        // TODO Auto-generated method stub
                    }

                    public void checkServerTrusted(X509Certificate[] chain,
                                                   String authType) throws CertificateException {
                        // TODO Auto-generated method stub

                    }

                    public X509Certificate[] getAcceptedIssuers() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                }
        };

        SSLContext sc;
        try {
            sc = SSLContext.getInstance("TLS");
            sc.init(null, trustEverythingTrustManager, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void networkTest() {

        try {
            trustAll();
            URL url = new URL(WIDGET_ENDPOINT_URL);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setHostnameVerifier(DO_NOT_VERIFY);
            connection.setRequestMethod("POST");

            // Set headers
            connection.setRequestProperty("Content-Type", "application/json");
//            connection.setRequestProperty("charset", "UTF-8");

            //Set timeouts
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);

            connection.setDoInput(true);
            connection.setDoOutput(true);

            JSONObject widgetData = new JSONObject();

            widgetData.put("name", "clock-and-weather");
            widgetData.put("row", "2");
            widgetData.put("col", "1");
            widgetData.put("sizeX", "3");
            widgetData.put("sizeY", "3");
            widgetData.put("delay", "0");


            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            System.out.print(widgetData.toString());
            writer.write(widgetData.toString());
            writer.flush();
            writer.close();
            os.close();

            connection.connect();

            int responseCode = connection.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public static void addWidget(Widget widget){
        widgetAPICall(widget, "POST");
    }

    public static void removeWidget(Widget widget){
        widgetAPICall(widget, "DELETE");
    }

    private static void widgetAPICall(final Widget widget, String method) {
        try {
            trustAll();
            URL url = new URL(WIDGET_ENDPOINT_URL);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setHostnameVerifier(DO_NOT_VERIFY);
            connection.setRequestMethod(method);

            // Set headers
            connection.setRequestProperty("Content-Type", "application/json");

            //Set timeouts
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);

            connection.setDoInput(true);
            connection.setDoOutput(true);

            JSONObject widgetData = new JSONObject();

            widgetData.put("name", widget.getApiName());
            widgetData.put("row", widget.getxPosition());
            widgetData.put("col", widget.getyPosition());
            widgetData.put("sizeX", widget.getWidth());
            widgetData.put("sizeY", widget.getHeight());
            widgetData.put("delay", "0");


            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            System.out.print(widgetData.toString());
            writer.write(widgetData.toString());
            writer.flush();
            writer.close();
            os.close();

            connection.connect();

            int responseCode = connection.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


    public static void exchengeCodeForToken(String code, String client_id, String client_secret) {
        if(code == null){
            return;
        }

        String urlParameters = "grant_type=authorization_code" +
                "&redirect_uri=postmessage" +
                "&code=" + code +
                "&client_id=" + client_id +
                "&client_secret=" + client_secret;
        try {
            trustAll();
//            URL url = new URL("https://www.googleapis.com/oauth2/v4/token");
            String urlString = "https://accounts.google.com/o/oauth2/token";

            //parameters grant_type=authorization_code&redirect_uri=postmessage&code=4/iOg0-7tGX7whkSQJBkXj8x7VA2jOWK43LgvyeGa9hXQ&client_id=483610214847-dal7psphmr4tvhgc6cqs2h5b62bo9eba.apps.googleusercontent.com&client_secret=L9YcHMfpSyYPwDKU9VVwnEUO
            URL url = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
//            connection.setHostnameVerifier(DO_NOT_VERIFY);
            connection.setRequestMethod("POST");

            // Set headers
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));

            //Set timeouts
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);

            connection.setDoInput(true);
            connection.setDoOutput(true);

//            JSONObject widgetData = new JSONObject();
//            widgetData.put("code", code);
//            widgetData.put("client_id", client_id);
//            widgetData.put("client_secret", client_secret);
//            widgetData.put("redirect_uri", "postmessage");
//            widgetData.put("grant_type", "authorization_code");

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            System.out.print(urlParameters);
            writer.write(urlParameters);
            writer.flush();
            writer.close();
            os.close();

            connection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();
            System.out.print(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
