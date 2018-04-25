package com.bill99.sign;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>签名加密方式</p>
 * @author markin
 * @version $Id: DigestUtil.java, v 0.1 2016年4月20日 下午9:12:32 ning.ma Exp $
 */
public enum DigestUtil implements Digest {
                                          RSA("RSA") {

                                              /**
                                               * 签名字符串
                                               *
                                               * @param text           需要签名的字符串
                                               * @param key            私钥(BASE64编码)
                                               * @param input_charset  编码格式
                                               * @return 签名结果(BASE64编码)
                                               * @throws Exception 
                                               */
                                              @Override
                                              public String sign(final String text,
                                                                 final String key,
                                                                 final Charset charset) throws Exception {
                                                  byte[] data = text.getBytes(charset);
                                                  byte[] keyBytes = Base64.decodeBase64(key);
                                                  PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
                                                  KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                                                  PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
                                                  Signature signature = Signature.getInstance("SHA1withRSA");
                                                  signature.initSign(privateK);
                                                  signature.update(data);
                                                  return Base64.encodeBase64String(signature.sign());
                                                 
                                              }

                                              /**
                                               * 签名字符串
                                               *
                                               * @param text           需要签名的字符串
                                               * @param sign           客户签名结果
                                               * @param key            公钥(BASE64编码)
                                               * @param input_charset  编码格式
                                               * @return 验签结果
                                               * @throws Exception 
                                               */
                                              @Override
                                              public boolean verify(final String text,
                                                                    final String sign,
                                                                    final String key,
                                                                    final Charset charset) throws Exception {
                                                  byte keyBytes[] = Base64.decodeBase64(key);
                                                  X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
                                                      keyBytes);
                                                  KeyFactory keyFactory = KeyFactory
                                                      .getInstance("RSA");
                                                  PublicKey publicK = keyFactory
                                                      .generatePublic(keySpec);
                                                  Signature signature = Signature
                                                      .getInstance("SHA1withRSA");
                                                  signature.initVerify(publicK);
                                                  signature.update(getContentBytes(text, charset));
                                                  return signature
                                                      .verify(Base64.decodeBase64(sign));
                                              }

                                          },
                                          MD5("MD5") {

                                              /**
                                               * MD5加密
                                               * @param text
                                               * @param key
                                               * @param charset
                                               * @return 签名结果
                                               * @throws Exception 
                                               */
                                              public String sign(final String text,
                                                                 final String key,
                                                                 final Charset charset) throws Exception {
                                                  return DigestUtils
                                                      .md5Hex(getContentBytes(text + key, charset));
                                              }

                                              /**
                                               * MD5校验
                                               * @param text
                                               * @param publicKey
                                               * @param publicKey
                                               * @param charset
                                               * @return 验签结果
                                               * @throws Exception 
                                               */
                                              @Override
                                              public boolean verify(final String text,
                                                                    final String sign,
                                                                    final String key,
                                                                    final Charset charset) throws Exception {
                                                  return StringUtils.equals(sign,
                                                      DigestUtils.md5Hex(
                                                          getContentBytes(text + key, charset)));
                                              }

                                          },;

    private String code;

    /**
     * 获取加密正文byte数据
     * @param content
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    byte[] getContentBytes(final String content,
                           final Charset charset) throws UnsupportedEncodingException {
        return null == charset ? content.getBytes() : content.getBytes(charset);
    }

    private DigestUtil(final String code) {
        this.code = code;
    }

    /**
     * @param code
     * @return
     */
    public final static Digest byCode(final String code) {
        if (isBlank(code)) {
            return null;
        }
        for (final DigestUtil digest : values()) {
            if (equalsIgnoreCase(digest.code, code)) {
                return digest;
            }
        }
        return null;
    }

}
