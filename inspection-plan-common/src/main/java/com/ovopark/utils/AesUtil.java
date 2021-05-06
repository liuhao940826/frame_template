package com.ovopark.utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;


/**
 * Aes加密算法工具类
 * @author zzg
 */
public class AesUtil {
	/** 
	* AES加密算法 
	*/ 
	public AesUtil() { 
	} 

	/** 
	* 加密 
	* 
	* @param content 
	*            需要加密的内容 
	* @param keyWord 
	*            加密密钥 
	* @return byte[] 加密后的字节数组 
	*/ 
	public static byte[] encrypt(String content, String keyWord) { 
	try { 
	KeyGenerator kgen = KeyGenerator.getInstance("AES"); 
	SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG"); 
	secureRandom.setSeed(keyWord.getBytes()); 
	kgen.init(128, secureRandom); 
	SecretKey secretKey = kgen.generateKey(); 
	byte[] enCodeFormat = secretKey.getEncoded(); 
	SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES"); 
	Cipher cipher = Cipher.getInstance("AES");// 创建密码器 
	byte[] byteContent = content.getBytes("utf-8"); 
	cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化 
	byte[] result = cipher.doFinal(byteContent); 
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

	/** 
	* @param content 
	*            需要加密的内容 
	* @param password 
	*            加密密钥 
	* @return String 加密后的字符串 
	*/ 
	public static String encrypttoStr(String content, String password) { 
	return parseByte2HexStr(encrypt(content, password)); 
	} 

	/** 
	* 解密 
	* 
	* @param content 
	*            待解密内容 
	* @param keyWord 
	*            解密密钥 
	* @return byte[] 
	*/ 
	public static byte[] decrypt(byte[] content, String keyWord) { 
	try { 
	KeyGenerator kgen = KeyGenerator.getInstance("AES"); 
	SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG"); 
	secureRandom.setSeed(keyWord.getBytes()); 
	kgen.init(128, secureRandom); 
	SecretKey secretKey = kgen.generateKey(); 
	byte[] enCodeFormat = secretKey.getEncoded(); 
	SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES"); 
	Cipher cipher = Cipher.getInstance("AES");// 创建密码器 
	cipher.init(Cipher.DECRYPT_MODE, key);// 初始化 
	byte[] result = cipher.doFinal(content); 
	return result; // 加密 
	} catch (NoSuchAlgorithmException e) { 
	e.printStackTrace(); 
	} catch (NoSuchPaddingException e) { 
	e.printStackTrace(); 
	} catch (InvalidKeyException e) { 
	e.printStackTrace(); 
	} catch (IllegalBlockSizeException e) { 
	e.printStackTrace(); 
	} catch (BadPaddingException e) { 
	e.printStackTrace(); 
	} 
	return null; 
	} 

	/** 
	* @param content 
	*            待解密内容(字符串) 
	* @param keyWord 
	*            解密密钥 
	* @return byte[] 
	*/ 
	public static byte[] decrypt(String content, String keyWord) { 
	return decrypt(parseHexStr2Byte(content), keyWord); 
	} 

	/** 
	* 将二进制转换成16进制 
	* 
	* @param buf 
	* @return String 
	*/ 
	public static String parseByte2HexStr(byte buf[]) { 
	StringBuffer sb = new StringBuffer(); 
	for (int i = 0; i < buf.length; i++) { 
	String hex = Integer.toHexString(buf[i] & 0xFF); 
	if (hex.length() == 1) { 
	hex = '0' + hex; 
	} 
	sb.append(hex.toUpperCase()); 
	} 
	return sb.toString(); 
	} 

	/** 
	* 将16进制转换为二进制 
	* 
	* @param hexStr 
	* @return byte[] 
	*/ 
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
	
	public static void main(String[] args) { 
	String content = "﻿﻿f59d98e3e3083495580b99c7c7719b35"; 
	String Key = "kedacom.com"; 

	// 加密 
	System.out.println("加密前：" + content); 
	String encryptResult = encrypttoStr(content, Key); 
	System.out.println("加密后：" + encryptResult); 
	// 解密 
	byte[] decryptResult = decrypt(encryptResult, Key); 
	System.out.println("解密后：" + new String(decryptResult)); 
	} 

	public static byte[] decrypt(String encryptedDataB64, String sessionKeyB64, String ivB64) throws Exception{
		//要解密的字符串
		byte[] dataByte = Base64.getDecoder().decode(encryptedDataB64);
		// 加密秘钥
		byte[] keyByte = Base64.getDecoder().decode(sessionKeyB64);
		// 偏移量
		byte[] ivByte = Base64.getDecoder().decode(ivB64);
	    try {
	        // 如果密钥不足16位，那么就补足. 
	        int base = 16;
	        if (keyByte.length % base != 0) {
	            int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
	            byte[] temp = new byte[groups * base];
	            Arrays.fill(temp, (byte) 0);
	            System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
	            keyByte = temp;
	        }
	        // 初始化
	//	            Security.addProvider(new BouncyCastleProvider());
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
	        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
	        parameters.init(new IvParameterSpec(ivByte));
	        cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
	        byte[] resultByte = cipher.doFinal(dataByte);
	        return resultByte;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
}
