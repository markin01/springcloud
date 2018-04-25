package com.bill99.sign;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
 * rsa工具类
 * 
 * @author easun
 *
 */
public class RSAUtil {

	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_MD5 = "MD5withRSA";
	public static final String SIGNATURE_SHA1 = "SHA1WithRSA";
	public static final String SIGNATURE_SHA256 = "SHA256WithRSA";

	/**
	 * 获取公钥的key
	 */
	private static final String PUBLIC_KEY = "RSAPublicKey";

	/**
	 * 获取私钥的key
	 */
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * 秘钥编码格式
	 */
	private static final String UTF8 = "UTF-8";

	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param contentData
	 *            签名数据
	 * @param privateKey
	 *            私钥
	 * @return 签名串
	 * @throws Exception
	 */
	public static String sign(Map<String, String> contentData, String privateKey, String charset) throws Exception {
		String stringSignTemp = coverMap2String(contentData);
		return sign(stringSignTemp, privateKey, charset);
	}

	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data
	 *            签名数据
	 * @param privateKey
	 *            私钥
	 * 
	 * @return 签名串
	 * @throws Exception
	 */
	public static String sign(String data, String privateKey, String charset) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(privateKey.getBytes(UTF8));
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance(SIGNATURE_SHA256);
		signature.initSign(privateK);
		signature.update(data.getBytes(charset));
		return Base64.encodeBase64String(signature.sign());
	}

	/**
	 * 校验数字签名
	 * 
	 * @param contentData
	 *            验签数据
	 * @param publicKey
	 *            公钥
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(Map<String, String> contentData, String publicKey, String charset) throws Exception {
		String orgSign = contentData.get("sign");
		if (null == orgSign || "".equals(orgSign)) {
			return false;
		}
		String stringSignTemp = coverMap2String(contentData);
		return verify(stringSignTemp, publicKey, orgSign, charset);
	}

	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            验签数据
	 * @param publicKey
	 *            公钥
	 * @param orgSign
	 *            原始数字签名
	 * 
	 * @return
	 * @throws Exception
	 * 
	 */
	public static boolean verify(String data, String publicKey, String orgSign, String charset) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(publicKey.getBytes(UTF8));
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGNATURE_SHA256);
		signature.initVerify(publicK);
		signature.update(data.getBytes(charset));
		return signature.verify(Base64.decodeBase64(orgSign.getBytes(charset)));
	}

	/**
	 * 将Map中的数据转换成按照Key的ascii码排序后的key1=value1&key2=value2的形式 不包含签名域signature
	 * 
	 * @param data
	 *            待拼接的Map数据
	 * @return 拼接好后的字符串
	 */
	public static String coverMap2String(Map<String, String> data) {
		TreeMap<String, String> tree = new TreeMap<>();
		Iterator<Entry<String, String>> it = data.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> en = it.next();
			if ("sign".equals(en.getKey().trim())) {
				continue;
			}
			if (null != en.getValue() && !"".equals(en.getValue())) {
				tree.put(en.getKey(), en.getValue());
			}
		}
		it = tree.entrySet().iterator();
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()) {
			Entry<String, String> en = it.next();
			sb.append(en.getKey() + "=" + en.getValue() + "&");
		}
		return sb.substring(0, sb.length() - 1);
	}

	/**
	 * 私钥加密
	 * 
	 * @param data
	 *            源数据
	 * @param privateKey
	 *            私钥
	 * @param charset
	 *            编码
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String privateKey, String charset) throws Exception {
		return Base64.encodeBase64String(encrypt(data.getBytes(charset), privateKey));
	}

	/**
	 * 私钥加密
	 * 
	 * @param data
	 *            源数据
	 * @param privateKey
	 *            私钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(privateKey.getBytes(UTF8));
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
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
		out.close();
		return encryptedData;
	}

	/**
	 * 公钥解密
	 * 
	 * @param data
	 *            已加密数据
	 * @param publicKey
	 *            公钥
	 * @param charset
	 *            编码
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String data, String publicKey, String charset) throws Exception {
		return new String(decrypt(Base64.decodeBase64(data.getBytes(charset)), publicKey), charset);
	}

	/**
	 * 公钥解密
	 * 
	 * @param encryptedData
	 *            已加密数据
	 * @param publicKey
	 *            公钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] encryptedData, String publicKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(publicKey.getBytes(UTF8));
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
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
		out.close();
		return decryptedData;
	}

	/**
	 * 生成密钥对(公钥和私钥)
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> genKeyPair() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024); // 秘钥长度，支持1024,2048
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * 获取私钥
	 * 
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return new String(Base64.encodeBase64(key.getEncoded()), UTF8);
	}

	/**
	 * 获取公钥
	 * 
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return new String(Base64.encodeBase64(key.getEncoded()), UTF8);
	}

}