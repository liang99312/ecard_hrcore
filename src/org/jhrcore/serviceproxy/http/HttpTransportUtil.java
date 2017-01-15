/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.serviceproxy.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpTransportUtil {
    public static HttpURLConnection getUrlConnection(String url)
            throws IOException {

        URL servletURL = new URL(url);
        HttpURLConnection servletConnection = (HttpURLConnection) servletURL.openConnection();
        servletConnection.setRequestMethod("POST");
        servletConnection.setDoOutput(true);
        servletConnection.setDoInput(true);
        servletConnection.setUseCaches(false);
//        servletConnection.setConnectTimeout(10000);
//        servletConnection.setReadTimeout(10000);

        servletConnection.setRequestProperty("Content-type",
                "application/octet-stream");
        return servletConnection;

    }

    public static HttpTransport loadFromInputStream(InputStream is)
            throws IOException, ClassNotFoundException {
        HttpTransport t = new HttpTransport();

        ObjectInputStream ois = new ObjectInputStream(is);
        t = (HttpTransport) ois.readObject();
        ois.close();
        
        return t;

    }

    public static void sendToOutputstream(OutputStream os, HttpTransport t)
            throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(t);
        oos.flush();
        oos.close();
    }    
}
