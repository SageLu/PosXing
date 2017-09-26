package sage.lu6gmail.com.posxing;

/**
 * 类名: HttpUtil
 * 此类用途: ---
 *
 * @Author: GuXiao
 * @Date: 2017-07-19 17:46
 * @Email: sage.lu6@gmail.com
 * @FileName: sage.lu6gmail.com.posxing.HttpUtil.java
 */

//import com.jd.jsf.gd.util.StringUtils;
//
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.utils.URIBuilder;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class HttpUtil {
    private static final String CONTENT_CHARSET = "UTF-8";
//    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static Map<String, String> getParam() {
        return new HashMap();
    }

//    public static String sendPost(String url, Map<String, String> params) {
//        String response = null;
//        PostMethod httpPost = null;
//        HttpClient httpClient = null;
//        try {
//            httpClient = new HttpClient();
//            httpPost = new PostMethod(url);
//            httpPost.getParams().setContentCharset("UTF-8");
//            httpClient.getHttpConnectionManager().getParams().setTcpNoDelay(true);
//            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
//            for (Map.Entry entry : params.entrySet()) {
//                httpPost.addParameter((String) entry.getKey(), (String) entry.getValue());
//            }
//            if (200 == httpClient.executeMethod(httpPost))
//                response = httpPost.getResponseBodyAsString();
//        } catch (IOException e) {
////            logger.error("sendPost(" + params + ") Exception", e);
//        } finally {
//            if (httpPost != null) {
//                httpPost.releaseConnection();
//            }
//            if (httpClient != null) {
//                httpClient.getHttpConnectionManager().closeIdleConnections(0L);
//            }
//        }
//        return response;
//    }
//
//
//    public static String sendGet(String url, Map<String, String> params) {
//        // 创建Httpclient对象
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        CloseableHttpResponse response = null;
//        try {
//            if (StringUtils.isBlank(url)) {
////                logger.info("get请求地址为空");
//                throw new RuntimeException("get请求地址为空");
//            }
//            URIBuilder uriBuilder = new URIBuilder(url);
//            if (params != null && params.size() != 0) {
//                for (Map.Entry<String, String> en : params.entrySet()) {
//                    String key = en.getKey();
//                    String value = en.getValue();
//                    uriBuilder.setParameter(key, value);
//                }
//            }
//            URI uri = uriBuilder.build();
//            // 创建http GET请求
//            HttpGet httpGet = new HttpGet(uri);
//            // 执行请求
//            response = httpclient.execute(httpGet);
//            // 判断返回状态是否为200
//            if (response.getStatusLine().getStatusCode() == 200) {
//                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
////                logger.info("get请求成功,url={} , result={}", url, content);
//                return content;
//            }
//        } catch (Exception e) {
////            logger.error("get请求失败,url={}", url, e);
//        } finally {
//            if (response != null) {
//                try {
//                    response.close();
//                } catch (IOException e) {
////                    logger.error("关闭httpresponse失败", e);
//                }
//            }
//            try {
//                httpclient.close();
//            } catch (IOException e) {
////                logger.error("关闭httpclient失败", e);
//            }
//
//        }
//        return null;
//    }

   /* public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("memberid","22318136");
        String s = sendGet("http://172.24.5.53:8092/authRequest/getAllAuthSubMitData.do", map);

        logger.info("get 返回,result={}",s);
    }*/


   //daqiang
   public static final String postDQ(String postUrl,  Map<String, String> params) {

//       if (!isNetworking()) {
//           return "net_error";
//       }
//       Log.e("xingpossage", params);
//       URIBuilder uriBuilder = new URIBuilder(url);
       StringBuilder stringBuilder = new StringBuilder();

       if (params != null && params.size() != 0) {
                for (Map.Entry<String, String> en : params.entrySet()) {
                    String key = en.getKey();
                    String value = en.getValue();
                    stringBuilder.append(key+"="+value+"&");
                }
            }

       String result = null;
       URL url = null;
       HttpURLConnection connection = null;
       InputStreamReader in = null;
       try {
//           postUrl += "?" + params;
           url = new URL(postUrl);
           connection = (HttpURLConnection) url.openConnection();
           connection.setConnectTimeout(10000);
           connection.setReadTimeout(10000);
           connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type",
//                    "text/xml; charset=utf-8");
           connection.setRequestProperty("Content-Type",
                   "application/x-www-form-urlencoded; charset=utf-8");
           connection.setDoOutput(true);//application/x-www-form-urlencoded
           DataOutputStream dop = new DataOutputStream(
                   connection.getOutputStream());
//           String
           dop.writeBytes(stringBuilder.toString().substring(0,stringBuilder.toString().length()-1));
           dop.flush();
           dop.close();

           in = new InputStreamReader(connection.getInputStream());
           BufferedReader bufferedReader = new BufferedReader(in);
           StringBuffer strBuffer = new StringBuffer();
           String line = null;
           while ((line = bufferedReader.readLine()) != null) {
               strBuffer.append(line);
           }
           result = strBuffer.toString();
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           if (connection != null) {
               connection.disconnect();
           }
           if (in != null) {
               try {
                   in.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }

       }
       return result;
   }
}