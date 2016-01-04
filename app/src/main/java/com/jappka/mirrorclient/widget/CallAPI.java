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
    private static final String SERVER_URL = "https://192.168.0.21:8000";
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
            connection.setRequestProperty("charset", "UTF-8");

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

            writer.write(widgetData.toString());
            writer.flush();
            writer.close();
            os.close();

            connection.connect();

            int responseCode = connection.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
//            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

//                InputStream error = connection.getErrorStream();

//            String str = CharStreams.toString(new InputStreamReader(error, "UTF-8"));
//            System.out.print("Erorr message: "+str);
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(connection.getInputStream()));
//            String inputLine;
//            StringBuffer response = new StringBuffer();
//
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//
//            //print result
//            System.out.println(response.toString());


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


    }

    /**
     * Translate List of Pairs of parameters to single String
     * @param params List of parameters (key, value Pair)
     * @return Parameters as a single String
     * @throws UnsupportedEncodingException
     */
    private static String getQuery(List<Pair<String, String>> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Pair<String, String> pair : params) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }

            result.append(URLEncoder.encode(pair.first, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.second, "UTF-8"));
        }

        return result.toString();
    }

}
