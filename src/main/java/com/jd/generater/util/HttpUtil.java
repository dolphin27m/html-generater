package com.jd.generater.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.CharBuffer;

/**
 * Created by caozhifei on 2015/11/19.
 */
public class HttpUtil {
    private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    public static final String ERROR = "ERROR";
    private static final String defaultEncoding = "UTF-8";

    public static String getStringFromUrl(String urlStr) {
        return getStringFromUrl(urlStr, null, null);
    }

    public static String getStringFromUrl(String urlStr, String encoding) {
        return getStringFromUrl(urlStr, encoding, null);
    }

    public static String getStringFromUrl(String urlStr, String encoding, String ip) {
        StringBuffer bs = new StringBuffer();
        if (encoding == null) {
            encoding = defaultEncoding;
        }
        try {
            URL url = new URL(urlStr);
            HttpURLConnection httpUrlConn = null;
            if (ip != null) {
                String str[] = ip.split("\\.");
                byte[] b = new byte[str.length];
                for (int i = 0, len = str.length; i < len; i++) {
                    b[i] = (byte) (Integer.parseInt(str[i], 10));
                }
                Proxy proxy = new Proxy(Proxy.Type.HTTP,
                        new InetSocketAddress(InetAddress.getByAddress(b), 80));  //b是绑定的ip，生成proxy代理对象，因为http底层是socket实现，
                httpUrlConn = (HttpURLConnection) url.openConnection(proxy);
            } else {
                httpUrlConn = (HttpURLConnection) url.openConnection();
            }
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setConnectTimeout(10000);
            httpUrlConn.setReadTimeout(10000);
            httpUrlConn.setDefaultUseCaches(false);
            httpUrlConn.setUseCaches(false);
            if (HttpURLConnection.HTTP_OK == httpUrlConn.getResponseCode()) {
                InputStream is = httpUrlConn.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(is, encoding));
                int len = 0;
                while (len != -1) {
                    char[] chars = new char[1];
                    len = buffer.read(chars);
                    if(len != -1) {
                        bs.append(chars);
                    }
                }
                buffer.close();
                return bs.toString();
            }
        } catch (Exception e) {
            logger.error("getStringFromUrl error,url is "+urlStr,e);
        }
        return ERROR;
    }

    public static void main(String[] args) {
       System.out.println( HttpUtil.getStringFromUrl("http://try.home.jd.com", "UTF-8", "172.17.26.36"));
    }
}
