package com.youhe.utils.pay.sdk.utils;

import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;


public class RsaUtils {

    private  static final String CHARSET = "UTF-8";
    
    private  static final String algorithm = "SHA256withRSA";
    /**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";
    
    /**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 245;
	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 256;
    
    /**
     * 请求报文签名
     * @param privateKey  机构私钥字符串
     * @param content     签名原文
     * @return            签名密文
     * @throws Exception
     */
    public static String sign(String privateKey,String content)throws Exception{
        Signature signature = Signature.getInstance(algorithm);
        signature.initSign(convertPrivateKey(privateKey));
        signature.update(content.getBytes(CHARSET));
        return Base64.encodeBase64String(signature.sign());
    }

    /**
     * 返回报文验签
     * @param publicKey   公钥字符串
     * @param content     验签原文报文
     * @param signStr     返回签名字符串
     * @return            验签结果
     * @throws Exception
     */
    public static boolean vertify(String publicKey,String content,String signStr)throws Exception{
        Signature signature = Signature.getInstance(algorithm);
        signature.initVerify(convertPublicKey(publicKey));
        signature.update(content.getBytes(CHARSET));
        return signature.verify(Base64.decodeBase64(signStr.getBytes(CHARSET)));
    }

	/**
	 * create by Kalvin 返回报文验签
	 * @param publicKeyIs   公钥字符串输入流
	 * @param content     验签原文报文
	 * @param signStr     返回签名字符串
	 * @return            验签结果
	 * @throws Exception
	 */
	public static boolean vertify(InputStream publicKeyIs, String content, String signStr)throws Exception{
		Signature signature = Signature.getInstance(algorithm);
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
		byte[] bytes = new byte[publicKeyIs.available()];
		publicKeyIs.read(bytes);
		Certificate certificate = certificateFactory.generateCertificate(new ByteArrayInputStream(bytes));
		signature.initVerify(certificate.getPublicKey());
		signature.update(content.getBytes(CHARSET));
		return signature.verify(Base64.decodeBase64(signStr.getBytes(CHARSET)));
	}
	

    /**
	 * <p>
	 * 公钥加密
	 * </p>
	 * 
	 * @param data
	 *            源数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
     * @throws Exception 
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String data, String publicKey) throws Exception {
		return encryptByPublicKey(data.getBytes(CHARSET), publicKey);
	}
    /**
	 * <p>
	 * 公钥加密
	 * </p>
	 * 
	 * @param data
	 *            源数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPublicKey(byte[] data, String publicKey) {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		String result = "";
		try
		{
			PublicKey publicK = convertPublicKey(publicKey);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			// 对数据加密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicK);
			
			int inputLen = data.length;
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = out.toByteArray();
//			result = Base64Utils.encode(encryptedData);
			result = java.util.Base64.getEncoder().encodeToString(encryptedData);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
		
		
	}
	
	/**
	 * <P>
	 * 私钥解密
	 * </p>
	 * 
	 * @param encryptedData
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(String encryptedData, String privateKey) throws Exception {
		
		return decryptByPrivateKey(Base64Utils.decode(encryptedData), privateKey);
	}
	/**
	 * <P>
	 * 私钥解密
	 * </p>
	 * 
	 * @param encryptedData
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(byte[] encryptedData, String privateKey) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String result = "";
		try
		{
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			PrivateKey privateK = convertPrivateKey(privateKey);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateK);
			int inputLen = encryptedData.length;
			
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			result = new String(decryptedData, "UTF-8");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
    private static PrivateKey convertPrivateKey(String keyStr)throws Exception{
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(keyStr.getBytes(CHARSET)));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    private  static PublicKey convertPublicKey(String keyStr)throws Exception{
    	 CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
         Certificate certificate = certificateFactory.generateCertificate(new ByteArrayInputStream(Base64.decodeBase64(keyStr.getBytes(CHARSET))));
         return certificate.getPublicKey();
    }

    public static String getPublicKey(String publicKeyPath){
    	if(publicKeyPath==null){
    		throw new RuntimeException("请设置公钥证书路径！");
    	}
    	return getKeyFromKeyPath(publicKeyPath);
    }
    
    public static String getPrivateKey(String privateKeyPath){
    	if(privateKeyPath==null){
    		throw new RuntimeException("请设置私钥证书路径！");
    	}
    	return getKeyFromKeyPath(privateKeyPath);
    }
    
   

	private static String getKeyFromKeyPath(String keyPath) {
		File file = new File(keyPath);
		if(!file.exists()){
			throw new RuntimeException("证书文件不存在:"+keyPath);
		}
		BufferedReader reader = null;
		StringBuffer keyString = new StringBuffer();
		String tempString = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			while ((tempString = reader.readLine()) != null) {
				if(tempString.startsWith("--")){
					continue;
				}
				keyString.append(tempString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return keyString.toString();
		
	}
}
