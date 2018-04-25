package com.bill99.sign;

import java.nio.charset.Charset;

/**
 * <p>加密</p>
 * @author markin
 * @version $Id: Sign.java, v 0.1 2016年4月20日 下午9:11:57 ning.ma Exp $
 */
public interface Digest {

    /**
     * 算法名称
     * @return
     */
    String name();

    /**
     * 加密算法
     * @param text
     * @param key
     * @param charset
     * @return
     */
    String sign(final String text, final String key, final Charset charset) throws Exception;

    /**
     * 数据校验
     * @param text
     * @param sign
     * @param key
     * @param charset
     * @return
     */
    boolean verify(final String text, final String sign, final String key,
                   final Charset charset) throws Exception;

}
