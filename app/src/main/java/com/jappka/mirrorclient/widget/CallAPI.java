package com.jappka.mirrorclient.widget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private static final String SERVER_URL = "https://192.168.1.7:8000";
    private static final String WIDGET_ENDPOINT_URL = SERVER_URL + "/api/widgets";
    private static final String TOKENS_ENDPOINT = SERVER_URL + "/api/google/token";
    private static final String COLOR_ENDPOINT = SERVER_URL + "/api/led";
    private static final String HDMI_ENDPOINT = SERVER_URL + "/api/hdmi";
    private static final String TWITTER_ENDPOINT = SERVER_URL + "/api/tweets";

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
                    }

                    public void checkServerTrusted(X509Certificate[] chain,
                                                   String authType) throws CertificateException {
                    }

                    public X509Certificate[] getAcceptedIssuers() {
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

    public static void addWidget(Widget widget){
        widgetAPICall(widget, "POST");
    }

    public static void removeWidget(Widget widget){
        widgetAPICall(widget, "DELETE");
    }


    public static void setColor(final String color) {
        try {
            trustAll();
            URL url = new URL(COLOR_ENDPOINT);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setHostnameVerifier(DO_NOT_VERIFY);
            connection.setRequestMethod("POST");

            // Set headers
            connection.setRequestProperty("Content-Type", "application/json");

            //Set timeouts
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);

            connection.setDoInput(true);
            connection.setDoOutput(true);

            JSONObject widgetData = new JSONObject();

            widgetData.put("color", color);


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

    public static Map<String, String> exchangeCodeForToken(String code, String client_id, String client_secret) {
        if(code == null){
            return null;
        }

        Map<String, String> tokens = new HashMap<>();
        String urlParameters = "grant_type=authorization_code" +
                "&redirect_uri=http://server.mirror.jappka.com" +
                "&code=" + code +
                "&client_id=" + client_id +
                "&client_secret=" + client_secret;

        try {
            trustAll();
            String urlString = "https://accounts.google.com/o/oauth2/token";

            URL url = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setHostnameVerifier(DO_NOT_VERIFY);
            connection.setRequestMethod("POST");

            // Set headers
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));

            //Set timeouts
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);

            connection.setDoInput(true);
            connection.setDoOutput(true);

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
                if(line.contains("access_token")){
                    String auth_token;
                    auth_token = line.replace("\"", "").replace(",", "").replace(" access_token : ", "").replace(" ", "");
                    tokens.put("auth_token", auth_token);
                    continue;
                }
                if(line.contains("refresh_token")){
                    String refresh_token;
                    refresh_token = line.replace("\"", "").replace(",","").replace(" refresh_token : ","").replace(" ", "");
                    tokens.put("refresh_token", refresh_token);
                }
            }

            br.close();

            sendTokens(tokens, "POST");
            System.out.print(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return tokens;
    }

    private static void sendTokens(Map<String, String> tokens, String method){
        if(method == "POST") {
            sendTokens(tokens, "DELETE");
        }
        try {
            trustAll();
            URL url = new URL(TOKENS_ENDPOINT);
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

            System.out.println(tokens.get("auth_token"));
            System.out.println(tokens.get("refresh_token"));

            widgetData.put("token", tokens.get("auth_token"));
            widgetData.put("refresh_token", tokens.get("refresh_token"));

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            System.out.println("=========== TOKENS ==========");
            System.out.println(widgetData.toString());
            writer.write(widgetData.toString());
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
            System.out.println("Response Mesage (tokens): " + sb);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }



    public static void turnOnMirror(String method){
        try {
            trustAll();
            URL url = new URL(HDMI_ENDPOINT);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setHostnameVerifier(DO_NOT_VERIFY);
            connection.setRequestMethod(method);

            // Set headers
            connection.setRequestProperty("Content-Type", "application/json");

            //Set timeouts
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);

            connection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            System.out.println("Response Mesage (tokens): " + sb);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void turnOffMirror(){
        turnOnMirror("DELETE");
    }

    public static void setTwitter(String text) {
        try {
            trustAll();
            URL url = new URL(TWITTER_ENDPOINT);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setHostnameVerifier(DO_NOT_VERIFY);
            connection.setRequestMethod("POST");

            // Set headers
            connection.setRequestProperty("Content-Type", "application/json");

            //Set timeouts
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);

            connection.setDoInput(true);
            connection.setDoOutput(true);

            JSONObject widgetData = new JSONObject();

            widgetData.put("phrase", text);

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            System.out.println(widgetData.toString());
            writer.write(widgetData.toString());
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
            System.out.println("Response Message (tweeter): " + sb);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
