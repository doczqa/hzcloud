package com.hzcloud.device.syris;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import java.security.cert.X509Certificate;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.HttpPut;

import java.io.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hzcloud.device.syris.bo.V8AddAuthorizationInfo;
import com.hzcloud.device.syris.bo.V8AddControllerInfo;
import com.hzcloud.device.syris.bo.V8AddDoorInfo;
import com.hzcloud.device.syris.bo.V8DeleteAuthorizationInfo;
import com.hzcloud.device.syris.bo.V8DeleteInfo;
import com.hzcloud.device.syris.bo.V8DeviceTopology;
import com.hzcloud.device.syris.bo.V8DevicesInfo;
import com.hzcloud.device.syris.bo.V8HolidayInfo;
import com.hzcloud.device.syris.bo.V8Record;
import com.hzcloud.device.syris.bo.V8RemoteDoorInfo;
import com.hzcloud.device.syris.utils.HttpDeleteWithBody;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class V8 {
    private static String  gatewayIdentifier;
    private static String  host;
    private static int port;

    public static void setHost(String host){
        V8.host = host;
    }

    public static void setPort(int port){
        V8.port = port;
    }

    public static String getGatewayIdentifier(){
        return gatewayIdentifier;
    }

    private CloseableHttpClient creatHttpClient(){
        try{
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, (X509Certificate[] x509Certificates, String s) -> true);
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", new PlainConnectionSocketFactory())
                        .register("https", socketFactory).build();
            HttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
            CloseableHttpClient  httpClient = HttpClients.custom().setConnectionManager(connManager).build();
            return httpClient;
        }catch(Exception e){
            log.error("系统异常:",e);
        }
        return null;
    }

    public static boolean Init(){
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try{
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, (X509Certificate[] x509Certificates, String s) -> true);
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", new PlainConnectionSocketFactory())
                        .register("https", socketFactory).build();
            HttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
            httpClient = HttpClients.custom().setConnectionManager(connManager).build();
            String url = "https://" + host + ":" + port + "/syris/cloud_gateway";
            HttpGet httpGet = new HttpGet(url);
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String content = EntityUtils.toString(responseEntity);
                JSONObject jsonObj = JSONObject.parseObject(content);
                JSONArray gwarray = jsonObj.getJSONArray("cloud_gateways");
                JSONObject gw = gwarray.getJSONObject(0);
                V8.gatewayIdentifier = gw.getString("identifier");
                return true;
            }
        } catch (Exception e) {
            System.out.println("连接失败"+e.getMessage());
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("系统异常:",e);
            }
        }
        return false;
    }

    //托管设备
    public String DeviceAssgin(V8DevicesInfo devices){
        CloseableHttpClient httpClient = this.creatHttpClient();
        if(httpClient == null){
            return "";
        }
        CloseableHttpResponse response = null;
        try{
            String url = "https://" + host + ":" + port + "/syris/device/assign";
            HttpPost httpPost = new HttpPost(url);
            String jsonString = JSON.toJSONString(devices);
            StringEntity entity = new StringEntity(jsonString, "UTF-8");
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String content = "["+ EntityUtils.toString(responseEntity)+"]";
                JSONArray jsonArr = JSONArray.parseArray(content);
                int arrlen = jsonArr.size();
                if(arrlen == 3) {
                	JSONObject res = jsonArr.getJSONObject(1);
                	String deviceIdentifier = res.getString("identifier");
                	return deviceIdentifier;
                }
            }
        } catch (Exception e) {
            System.out.println("连接失败"+e.getMessage());
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("系统异常:",e);
            }
        }
        return "";
    }

    //重载设备
    public boolean DeviceReload(){
        CloseableHttpClient httpClient = this.creatHttpClient();
        if(httpClient == null){
            return false;
        }
        CloseableHttpResponse response = null;
        try{
            String url = "https://" + host + ":" + port + "/syris/device";
            HttpPut httpPut = new HttpPut(url);
            response = httpClient.execute(httpPut);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String content = EntityUtils.toString(responseEntity);
                JSONObject jsonObj = JSONObject.parseObject(content);
                Long reslut = jsonObj.getLong("result");
                if(reslut == 0){
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("连接失败"+e.getMessage());
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("系统异常:",e);
            }
        }
        return false;
    }

    //获取设备拓扑
    public V8DeviceTopology DeviceTopology(String deviceIdentifier){
        CloseableHttpClient httpClient = this.creatHttpClient();
        if(httpClient == null){
            return null;
        }
        CloseableHttpResponse response = null;
        try{
            URIBuilder uriBuilder=new URIBuilder("https://" + host + ":" + port + "/syris/device/topology");
            uriBuilder.addParameter("identifier", deviceIdentifier);
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String content = EntityUtils.toString(responseEntity);
                V8DeviceTopology topology = JSON.parseObject(content,V8DeviceTopology.class);
                return topology;
            }
        } catch (Exception e) {
            System.out.println("连接失败"+e.getMessage());
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("系统异常:",e);
            }
        }
        return null;
    }

    //获取设备记录
    public List<V8Record> V8record(int index){
        CloseableHttpClient httpClient = this.creatHttpClient();
        if(httpClient == null){
            return null;
        }
        CloseableHttpResponse response = null;
        try{
            URIBuilder uriBuilder=new URIBuilder("https://" + host + ":" + port + "/syris/device/record_cache");
            uriBuilder.addParameter("index", "" + index);
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String content = "["+ EntityUtils.toString(responseEntity)+"]";
                JSONArray jsonArr = JSONArray.parseArray(content);
                int arrlen = jsonArr.size();
                if(arrlen > 1){
                    List<V8Record> recordList = new ArrayList<V8Record>();
                    for(int i = 0; i != arrlen -1; i++){
                        JSONObject recordjson = jsonArr.getJSONObject(i);
                        V8Record record = JSON.toJavaObject(recordjson,V8Record.class);
                        recordList.add(record);
                    }
                    return recordList;
                }
            }
        } catch (Exception e) {
            System.out.println("连接失败"+e.getMessage());
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("系统异常:",e);
            }
        }
        return null;
    }

    //添加控制器
    public String  AddController(V8AddControllerInfo addControllerInfo){
        CloseableHttpClient httpClient = this.creatHttpClient();
        if(httpClient == null){
            return "";
        }
        CloseableHttpResponse response = null;
        try{
            String url = "https://" + host + ":" + port + "/syris/controller";
            HttpPost httpPost = new HttpPost(url);
            String jsonString = JSON.toJSONString(addControllerInfo);
            StringEntity entity = new StringEntity(jsonString, "UTF-8");
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String content = EntityUtils.toString(responseEntity);
                JSONObject jsonObj = JSONObject.parseObject(content);
                String identifier = jsonObj.getString("identifier");
                return identifier;
            }
        } catch (Exception e) {
            System.out.println("连接失败"+e.getMessage());
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("系统异常:",e);
            }
        }
        return "";
    }

    //删除控制器
    public boolean DeleteController(V8DeleteInfo deleteControllerInfo){
        CloseableHttpClient httpClient = this.creatHttpClient();
        if(httpClient == null){
            return false;
        }
        CloseableHttpResponse response = null;
        try{
            String url = "https://" + host + ":" + port + "/syris/controller";
            HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(url);
            String jsonString = JSON.toJSONString(deleteControllerInfo);
            StringEntity entity = new StringEntity(jsonString, "UTF-8");
            httpDelete.setEntity(entity);
            httpDelete.setHeader("Content-Type", "application/json;charset=utf8");
            response = httpClient.execute(httpDelete);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String content = EntityUtils.toString(responseEntity);
                JSONObject jsonObj = JSONObject.parseObject(content);
                Long result = jsonObj.getLong("result");
                if(result == 0){
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("连接失败"+e.getMessage());
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("系统异常:",e);
            }
        }
        return false;
    }

    //控制器添加认证
    public boolean AddAuthorization(V8AddAuthorizationInfo addAuthorizationInfo){
        CloseableHttpClient httpClient = this.creatHttpClient();
        if(httpClient == null){
            return false;
        }
        CloseableHttpResponse response = null;
        try{
            String url = "https://" + host + ":" + port + "/syris/controller/authorization";
            HttpPost httpPost = new HttpPost(url);
            String jsonString = JSON.toJSONString(addAuthorizationInfo);
            StringEntity entity = new StringEntity(jsonString, "UTF-8");
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String content = "["+ EntityUtils.toString(responseEntity)+"]";
                JSONArray jsonArr = JSONArray.parseArray(content);
                int arrlen = jsonArr.size();
                if(arrlen == 3) {
                	JSONObject res = jsonArr.getJSONObject(1);
                	Long reslut = res.getLong("reslut");
                	if(reslut == 0){
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("连接失败"+e.getMessage());
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("系统异常:",e);
            }
        }
        return false;
    }

    //控制器删除认证
    public boolean DeleteAuthorization(V8DeleteAuthorizationInfo deleteAuthorizationInfo){
        CloseableHttpClient httpClient = this.creatHttpClient();
        if(httpClient == null){
            return false;
        }
        CloseableHttpResponse response = null;
        try{
            String url = "https://" + host + ":" + port + "/syris/controller/authorization";
            HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(url);
            String jsonString = JSON.toJSONString(deleteAuthorizationInfo);
            StringEntity entity = new StringEntity(jsonString, "UTF-8");
            httpDelete.setEntity(entity);
            httpDelete.setHeader("Content-Type", "application/json;charset=utf8");
            response = httpClient.execute(httpDelete);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String content = "["+ EntityUtils.toString(responseEntity)+"]";
                JSONArray jsonArr = JSONArray.parseArray(content);
                int arrlen = jsonArr.size();
                if(arrlen == 3) {
                	JSONObject res = jsonArr.getJSONObject(1);
                	Long reslut = res.getLong("reslut");
                	if(reslut == 0){
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("连接失败"+e.getMessage());
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("系统异常:",e);
            }
        }
        return false;
    }

    //控制器设置假日
    public boolean SetHoliday(V8HolidayInfo holidayInfo){
        CloseableHttpClient httpClient = this.creatHttpClient();
        if(httpClient == null){
            return false;
        }
        CloseableHttpResponse response = null;
        try{
            String url = "https://" + host + ":" + port + "/syris/controller/holiday";
            HttpPut httpPut = new HttpPut(url);
            String jsonString = JSON.toJSONString(holidayInfo);
            StringEntity entity = new StringEntity(jsonString, "UTF-8");
            httpPut.setEntity(entity);
            httpPut.setHeader("Content-Type", "application/json;charset=utf8");
            response = httpClient.execute(httpPut);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String content = EntityUtils.toString(responseEntity);
                JSONObject jsonObj = JSONObject.parseObject(content);
                Long reslut = jsonObj.getLong("result");
                if(reslut == 0){
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("连接失败"+e.getMessage());
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("系统异常:",e);
            }
        }
        return false;
    }

    //获取控制器在线状态
    public boolean GetControllerStatus(String identifier){
        CloseableHttpClient httpClient = this.creatHttpClient();
        if(httpClient == null){
            return false;
        }
        CloseableHttpResponse response = null;
        try{
            URIBuilder uriBuilder=new URIBuilder("https://" + host + ":" + port + "/syris/controller/status");
            uriBuilder.addParameter("identifier", identifier);
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String content = EntityUtils.toString(responseEntity);
                JSONObject jsonObj = JSONObject.parseObject(content);
                Long reslut = jsonObj.getLong("reslut");
                if(reslut != 0){
                    return false;
                }
                JSONArray controllers = jsonObj.getJSONArray("controllers");
                JSONObject controller = controllers.getJSONObject(0);
                JSONObject status = controller.getJSONObject("status");
                Integer is_connected = status.getInteger("is_connected");
                if (is_connected == 1){
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("连接失败"+e.getMessage());
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("系统异常:",e);
            }
        }
        return false;
    }

    //加入门
    public String AddDoor(V8AddDoorInfo addDoorInfo){
        CloseableHttpClient httpClient = this.creatHttpClient();
        if(httpClient == null){
            return "";
        }
        CloseableHttpResponse response = null;
        try{
            String url = "https://" + host + ":" + port + "/syris/door";
            HttpPost httpPost = new HttpPost(url);
            String jsonString = JSON.toJSONString(addDoorInfo);
            StringEntity entity = new StringEntity(jsonString, "UTF-8");
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String content = EntityUtils.toString(responseEntity);
                JSONObject jsonObj = JSONObject.parseObject(content);
                String identifier = jsonObj.getString("identifier");
                return identifier;
            }
        } catch (Exception e) {
            System.out.println("连接失败"+e.getMessage());
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("系统异常:",e);
            }
        }
        return "";
    }
    
    //删除门
    public boolean DeleteDoor(V8DeleteInfo deleteDoorInfo){
        CloseableHttpClient httpClient = this.creatHttpClient();
        if(httpClient == null){
            return false;
        }
        CloseableHttpResponse response = null;
        try{
            String url = "https://" + host + ":" + port + "/syris/door";
            HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(url);
            String jsonString = JSON.toJSONString(deleteDoorInfo);
            StringEntity entity = new StringEntity(jsonString, "UTF-8");
            httpDelete.setEntity(entity);
            httpDelete.setHeader("Content-Type", "application/json;charset=utf8");
            response = httpClient.execute(httpDelete);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String content = EntityUtils.toString(responseEntity);
                JSONObject jsonObj = JSONObject.parseObject(content);
                Long result = jsonObj.getLong("result");
                if(result == 0){
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("连接失败"+e.getMessage());
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("系统异常:",e);
            }
        }
        return false;  
    }

    //控制门
    public boolean RemoteDoor(V8RemoteDoorInfo remoteDoorInfo){
        CloseableHttpClient httpClient = this.creatHttpClient();
        if(httpClient == null){
            return false;
        }
        CloseableHttpResponse response = null;
        try{
            String url = "https://" + host + ":" + port + "/syris/door/remote";
            HttpPost httpPost = new HttpPost(url);
            String jsonString = JSON.toJSONString(remoteDoorInfo);
            StringEntity entity = new StringEntity(jsonString, "UTF-8");
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String content = "["+ EntityUtils.toString(responseEntity)+"]";
                JSONArray jsonArr = JSONArray.parseArray(content);
                int arrlen = jsonArr.size();
                if(arrlen == 3) {
                	JSONObject res = jsonArr.getJSONObject(1);
                	Long reslut = res.getLong("reslut");
                	if(reslut == 0){
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("连接失败"+e.getMessage());
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("系统异常:",e);
            }
        }
        return false;
    }

    //获取门在线状态
    public boolean GetDoorStatus(String identifier){
        CloseableHttpClient httpClient = this.creatHttpClient();
        if(httpClient == null){
            return false;
        }
        CloseableHttpResponse response = null;
        try{
            URIBuilder uriBuilder=new URIBuilder("https://" + host + ":" + port + "/syris/door/status");
            uriBuilder.addParameter("identifier", identifier);
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String content = EntityUtils.toString(responseEntity);
                JSONObject jsonObj = JSONObject.parseObject(content);
                Long reslut = jsonObj.getLong("reslut");
                if(reslut != 0){
                    return false;
                }
                JSONArray doors = jsonObj.getJSONArray("doors");
                JSONObject door = doors.getJSONObject(0);
                JSONObject status = door.getJSONObject("status");
                Integer is_connected = status.getInteger("is_connected");
                if (is_connected == 1){
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("连接失败"+e.getMessage());
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("系统异常:",e);
            }
        }
        return false;
    }
}
