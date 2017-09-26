package sage.lu6gmail.com.posxing;

/**
 * 类名: HttpUtilTest
 * 此类用途: ---
 *
 * @Author: GuXiao
 * @Date: 2017-07-19 17:49
 * @Email: sage.lu6@gmail.com
 * @FileName: sage.lu6gmail.com.posxing.HttpUtilTest.java
 */

//import javax.crypto.spec.SecretKeySpec;
//
//        import com.google.gson.Gson;
//        import sun.misc.BASE64Encoder;
//
//        import javax.crypto.*;
//        import javax.crypto.spec.SecretKeySpec;
//        import java.io.UnsupportedEncodingException;
//        import java.security.InvalidKeyException;
//        import java.security.MessageDigest;
//        import java.security.NoSuchAlgorithmException;
//        import java.security.SecureRandom;
//        import java.text.SimpleDateFormat;
//        import java.util.*;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * 类描述：
 *
 * @author liangyuwu
 * @Time 2017/6/6 15:45
 */
public class HttpUtilTest {
    public static void main(String[] args) throws Exception {
        String key = "a49a198291da54d54a61327ae93d0177";//测试key,正式环境需要申请abcdefg123456789
        String requestId = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String merchantNo = "22294531";
        Gson gson = new Gson();
//[{"omType":"010001","omAdd":"测试地址啊啊啊","omName":"测试aaa","omId":"1"}]

        Map<String, String> rs = new HashMap<String, String>();
        rs.put("omId","1");
        rs.put("omName","测试aaa");
//        rs.put("omRt","1496738496100");
        rs.put("omType","010001");
        rs.put("omAdd","测试地址啊啊啊");
//        rs.put("agentId","123456");
//        rs.put("agentName","123456");

//        Map<String, String> rs2 = new HashMap<String, String>();
//        rs2.put("omId","1234567");
//        rs2.put("omName","1234560");
//        rs2.put("omRt","1496738445200");
//        rs2.put("omType","1234560");
//        rs2.put("omAdd","中国");
//        rs2.put("agentId","1234560");
//        rs2.put("agentName","1234560");
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//        list.add(rs2);
        list.add(rs);


        String paymentJson = gson.toJson(list);
paymentJson="[{\"omType\":\"010001\",\"omAdd\":\"测试地址啊啊啊\",\"omName\":\"测试aaa\",\"omId\":\"1\"}]";
        String data = new BASE64Encoder().encode(encrypt(paymentJson,key));
        String data1=aesEncrypt(paymentJson,key);
        System.out.println("data1=="+data1);
        //签名
//        String sign = md5(data,key);
        String sss=data+key;
        String sign = md5.MD5(sss);
        System.out.println("ssssssssssssahfduiafh=="+sss);


        System.out.println("paymentJson=="+paymentJson);
        System.out.println("data=="+data);
        System.out.println("sign=="+sign);

        Map<String, String> paymentParams = new HashMap<String, String>();
        paymentParams.put("requestId", requestId);
        paymentParams.put("merchantNo", merchantNo);//P110228823
        paymentParams.put("sign", sign);
        paymentParams.put("data",data);

        //String s=HttpUtil.sendPost("http://172.24.5.65:8080/channel/service.do", paymentParams);
//        String s=HttpUtil.postDQ("http://channel-info.jdpay.com/channel/service.do", paymentParams);
        //String s=HttpUtil.sendPost("http://channel-info.jdpay.com/channel/service.do", paymentParams);
//        System.out.println(s);

    }
    public static String aesEncrypt(String str, String key) throws Exception {
        if (str == null || key == null) return null;
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        System.out.println("aaaaaaaaaa");
        //key="qJUsKpxeURRhK5/ZhXc90Q==";
//        byte[] byteContent = new BASE64Decoder().decodeBuffer(key);
        byte[] byteContent =key.getBytes();
//        String aaa= String.valueOf(byteContent);
        String Ddadaf = new String(byteContent, "UTF-8");
        System.out.println("aaaaaaaaaaaaa===="+Ddadaf);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(byteContent, "AES"));
        byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));
        return new BASE64Encoder().encode(bytes);
    }
    private static String md5(String text, String key){
        byte[] bytes = (text + key).getBytes();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(bytes);
        bytes = messageDigest.digest();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < bytes.length; ++i) {
            if((bytes[i] & 255) < 16) {
                sb.append("0");
            }

            sb.append(Long.toString((long)(bytes[i] & 255), 16));
        }

        return sb.toString();
    }

    public static byte[] encrypt(String content, String password) throws IOException {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes());
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            String encode = new BASE64Encoder().encode(enCodeFormat);
            byte[] bytes = new BASE64Decoder().decodeBuffer(encode);
            System.out.println("daqiang=="+new BASE64Encoder().encode(enCodeFormat));
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");

            String Ddadaf = new String(enCodeFormat, "utf-8");
            System.out.println("bbbbbbbbbbb====="+Ddadaf);

            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            System.out.println("random=="+random.toString());
            System.out.println("secretKey=="+secretKey.toString());
            System.out.println("key=="+key.toString());
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
