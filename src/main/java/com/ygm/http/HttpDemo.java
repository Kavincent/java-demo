package com.ygm.http;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * http协议
 * 请求协议方法有：
 *  <UL>
 *  <LI>GET
 *  <LI>POST
 *  <LI>HEAD
 *  <LI>OPTIONS
 *  <LI>PUT
 *  <LI>DELETE
 *  <LI>TRACE
 * </UL> are legal, subject to protocol restrictions.  The default
 * Created by admin on 2018/12/20.
 */
public class HttpDemo {

    public static final String REQUEST_CHARSET = "UTF-8";
    public static final String RESPONSE_CHARSET = "UTF-8";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";
    /**
     * http请求的 Content-Type:Content-Type
     */
    public static final String CONTENT_TYPE = "text/html; charset=utf-8";
    public static final int CONNECT_TIMEOUT = 30000;
    public static final int READ_TIMEOUT = 30000;

    public static Map<String,String> doGet(String url, Map<String, String> parameters ){
        URL servletURL = null;
        HttpURLConnection connection = null;
        InputStream ins = null;
        ByteArrayOutputStream bos = null;

        Map<String,String> repsone = new HashMap<>();
        try {
            if (parameters != null && parameters.size() > 0) {
                StringBuffer queryString = new StringBuffer();
                for (String name : parameters.keySet()) {
                    if (StringUtils.isNotBlank(parameters.get(name))) {
                        queryString.append(name).append("=").append(URLEncoder.encode(parameters.get(name),REQUEST_CHARSET ))
                                .append("&");
                    }else{
                        queryString.append(name).append("=").append("&");

                    }
                }
                servletURL = new URL(url + "?" + queryString.substring(0,queryString.length()-1));
            }else{
                servletURL = new URL(url);
            }
            connection = (HttpURLConnection)servletURL.openConnection();
            // 设置连接超时，单位：毫秒
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            // 设置读操作超时，单位：毫秒
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setRequestMethod(METHOD_GET);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // 模拟浏览器
            connection.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");

            connection.connect();
            if(connection.getResponseCode() == 200){
                ins = new BufferedInputStream(connection.getInputStream());
                bos = new ByteArrayOutputStream();
                int len = 0;
                byte[] str = new byte[1024];
                while((len = ins.read(str)) != -1){
                    bos.write(str,0,len);
                }
                byte[] bosByte = bos.toByteArray();

                Map<String,List<String>> map = connection.getHeaderFields();
                for(String name : map.keySet()){
                    StringBuffer values = new StringBuffer();
                    for(String value : map.get(name)){
                        values.append(value).append(",");
                    }
                    repsone.put(name,values.toString().substring(0,values.toString().length()-1));
                }

                String repsoneMsg = new String(bosByte,RESPONSE_CHARSET);
                repsone.put("body",repsoneMsg);
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(ins != null){
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                connection.disconnect();
            }
        }
        return repsone;

    }
    public static String doGet(String url){

        URL servletURL = null;
        HttpURLConnection connection = null;
        InputStream ins = null;
        ByteArrayOutputStream bos = null;

        String repsoneMsg = "";
        try {
            servletURL = new URL(url);
            connection = (HttpURLConnection)servletURL.openConnection();
            // 设置连接超时，单位：毫秒
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            // 设置读操作超时，单位：毫秒
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setRequestMethod(METHOD_GET);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");

            connection.connect();
            if(connection.getResponseCode() == 200){
                ins = new BufferedInputStream(connection.getInputStream());
                bos = new ByteArrayOutputStream();
                int len = 0;
                byte[] str = new byte[1024];
                while((len = ins.read(str)) != -1){
                    bos.write(str,0,len);
                }
                byte[] bosByte = bos.toByteArray();

                repsoneMsg = new String(bosByte,RESPONSE_CHARSET);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(ins != null){
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                connection.disconnect();
            }
        }
        return repsoneMsg;
    }

    public static String doPost(String url){
      return doPost(url, null);
    }

    public static String doPost(String url,Map<String, String> parameters){

        URL servletURL = null;
        HttpURLConnection connection = null;
        InputStream ins = null;
        ByteArrayOutputStream bos = null;

        String repsoneMsg = "";
        try {
            if (parameters != null && parameters.size() > 0) {
                StringBuffer queryString = new StringBuffer();
                for (String name : parameters.keySet()) {
                    if (StringUtils.isNotBlank(parameters.get(name))) {
                        queryString.append(name).append("=").append(URLEncoder.encode(parameters.get(name), REQUEST_CHARSET))
                                .append("&");
                    } else {
                        queryString.append(name).append("=").append("&");

                    }
                }
                servletURL = new URL(url + "?" + queryString.substring(0, queryString.length() - 1));
            } else {
                servletURL = new URL(url);
            }
            connection = (HttpURLConnection)servletURL.openConnection();
            // 设置通用属性请求
            // 设置连接超时，单位：毫秒
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            // 设置读操作超时，单位：毫秒
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setRequestMethod(METHOD_POST);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //  connection.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");
            // 建立实际连接
            connection.connect();
            if(connection.getResponseCode() == 200){
                ins = new BufferedInputStream(connection.getInputStream());
                bos = new ByteArrayOutputStream();
                int len = 0;
                byte[] str = new byte[1024];
                while((len = ins.read(str)) != -1){
                    bos.write(str,0,len);
                }
                byte[] bosByte = bos.toByteArray();

                repsoneMsg = new String(bosByte,RESPONSE_CHARSET);
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(ins != null){
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                connection.disconnect();
            }
        }
        return repsoneMsg;

    }

    public static void main(String[] args) {
       System.out.println(doGet("http://www.runoob.com/tcpip/tcpip-email.html",null));
     //   System.out.println(doPost("http://www.runoob.com/tcpip/tcpip-email.html"));
    }

}
