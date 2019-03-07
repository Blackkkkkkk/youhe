package com.youhe.utils.pay.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Enumeration;

@SuppressWarnings("rawtypes")
public class PFXConverter {
	public static final String PKCS12 = "PKCS12";
	public static final String JKS = "JKS";

	/**
	 * 将pfx或p12的文件转为keystore
	 */
	public static void pfx2KeyStore(String pfxFile, String keyStoreFile, String pfxPass, String keyStorePass) {
		try {
			KeyStore inputKeyStore = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(pfxFile);
			char[] nPassword = null;

			if ((keyStorePass == null) || keyStorePass.trim().equals("")) {
				nPassword = null;
			} else {
				nPassword = pfxPass.toCharArray();
			}

			inputKeyStore.load(fis, nPassword);
			fis.close();

			KeyStore outputKeyStore = KeyStore.getInstance("JKS");

			outputKeyStore.load(null, keyStorePass.toCharArray());

			Enumeration enums = inputKeyStore.aliases();

			while (enums.hasMoreElements()) { 

				String keyAlias = (String) enums.nextElement();
				System.out.println("alias=[" + keyAlias + "]");

				if (inputKeyStore.isKeyEntry(keyAlias)) {
					Key key = inputKeyStore.getKey(keyAlias, nPassword);
					Certificate[] certChain = inputKeyStore.getCertificateChain(keyAlias);

					outputKeyStore.setKeyEntry(keyAlias, key, keyStorePass.toCharArray(), certChain);
				}
			}

			FileOutputStream out = new FileOutputStream(keyStoreFile);

			outputKeyStore.store(out, nPassword);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将keystore转为pfx
	 */
	public static void keyStore2Pfx(String keyStoreFile, String pfxFile, String keyStorePass) {
		try {
			KeyStore inputKeyStore = KeyStore.getInstance("JKS");
			FileInputStream fis = new FileInputStream(keyStoreFile);
			char[] nPassword = null;

			if ((keyStorePass == null) || keyStorePass.trim().equals("")) {
				nPassword = null;
			} else {
				nPassword = keyStorePass.toCharArray();
			}

			inputKeyStore.load(fis, nPassword);
			fis.close();

			KeyStore outputKeyStore = KeyStore.getInstance("PKCS12");

			outputKeyStore.load(null, keyStorePass.toCharArray());

			Enumeration enums = inputKeyStore.aliases();

			while (enums.hasMoreElements()) {
				String keyAlias = (String) enums.nextElement();

				System.out.println("alias=[" + keyAlias + "]");

				if (inputKeyStore.isKeyEntry(keyAlias)) {
					Key key = inputKeyStore.getKey(keyAlias, nPassword);
					Certificate[] certChain = inputKeyStore.getCertificateChain(keyAlias);

					outputKeyStore.setKeyEntry(keyAlias, key, keyStorePass.toCharArray(), certChain);
				}
			}

			FileOutputStream out = new FileOutputStream(pfxFile);

			outputKeyStore.store(out, nPassword);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		final String pfxFile = "E:\\cer\\jg0.pfx";
		final String keyStorePass = "11111111";
		final String keyStoreFile = "E:\\\\cer\\\\1.keystore";
		pfx2KeyStore(pfxFile, keyStoreFile, "11111111", keyStorePass);
	}
}