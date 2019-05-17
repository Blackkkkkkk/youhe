package com.youhe.utils.pay.sdk.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Enumeration;

/**
 * Created by dhcao on 2018/1/16.
 * 读取证书文件为公私钥
 */
public class KeyUtil {

    private static KeyStore ks;


    static {
        try {
            ks = KeyStore.getInstance("PKCS12");
            // 获得密钥库文件流
//            InputStream is = new FileInputStream(com.youhe.utils.pay.sdk.utils.Config.getPrivateKeyFile());
            InputStream is = Config.getPrivateKeyInputStream();
            // 加载密钥库
            ks.load(is, Config.getPrivateKeyPassword().toCharArray());

            // 关闭密钥库文件流
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取证书私钥字符串
     *
     * @return
     * @throws Exception
     */
    public static String getPrimaryKey() throws Exception {

        try {
            ks = KeyStore.getInstance("PKCS12");
            // 获得密钥库文件流
//            InputStream is = new FileInputStream(com.youhe.utils.pay.sdk.utils.Config.getPrivateKeyFile());
            InputStream is = Config.getPrivateKeyInputStream();
            // 加载密钥库
            ks.load(is, com.youhe.utils.pay.sdk.utils.Config.getPrivateKeyPassword().toCharArray());

            // 关闭密钥库文件流
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String keyAlias = getKeyAlias();
        PrivateKey privateKey = (PrivateKey) ks.getKey(keyAlias, com.youhe.utils.pay.sdk.utils.Config.getPrivateKeyPassword().toCharArray());
        String privateKeyStr = Base64.encodeBase64String(privateKey.getEncoded());
        return privateKeyStr;
    }

    /**
     * 获取证书公钥字符串
     *
     * @return
     * @throws Exception
     */
//    public static String getPublicKey() throws Exception{
//        String keyAlias = getKeyAlias();
//        //公钥
//        Certificate certificate = ks.getCertificate(keyAlias);
//
//        certificate.getPublicKey().getAlgorithm();
//        String publicKeyStr = Base64.encodeBase64String(certificate.getPublicKey().getEncoded());
//        return publicKeyStr;
//    }
//
    public static String getKeyAlias() throws Exception {
        @SuppressWarnings("rawtypes")
        Enumeration aliases = ks.aliases();
        String keyAlias = null;

        if (aliases.hasMoreElements()) {
            keyAlias = (String) aliases.nextElement();
        }
        return keyAlias;
    }

    public static void main(String[] args) {
        try {
            String keyAlias = getKeyAlias();
            PrivateKey privateKey = (PrivateKey) ks.getKey(keyAlias, com.youhe.utils.pay.sdk.utils.Config.getPrivateKeyPassword().toCharArray());
            String privateKeyStr = Base64.encodeBase64String(privateKey.getEncoded());
            System.out.println(privateKeyStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
