/* <p>文件名称: PasswordEncodingUtil.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-7-3</p>
 * <p>完成日期：2013-7-3</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-7-3 下午5:12:41
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.security.utils;

import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Implementation of PasswordEncoder using message digest. Can accept any
 * message digest that the JDK can accept, including MD5 and SHA1. Returns the
 * equivalent Hash you would get from a Perl digest.
 */
public final class PasswordEncodingUtil {

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private final String encodingAlgorithm;

    private String characterEncoding = "utf-8";
    
    public static final String ENCODING_ALGORITHM_MD5 = "MD5";
    public static final String ENCODING_ALGORITHM_SHA1 = "SHA1";

    public PasswordEncodingUtil(final String encodingAlgorithm) {
        this.encodingAlgorithm = encodingAlgorithm;
    }

    public String encode(final String password) {
        if (password == null) {
            return null;
        }

        try {
            MessageDigest messageDigest = MessageDigest
                .getInstance(this.encodingAlgorithm);

            if (StringUtils.hasText(this.characterEncoding)) {
                messageDigest.update(password.getBytes(this.characterEncoding));
            } else {
                messageDigest.update(password.getBytes());
            }


            final byte[] digest = messageDigest.digest();

            return getFormattedText(digest);
        } catch (final NoSuchAlgorithmException e) {
            throw new SecurityException(e);
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Takes the raw bytes from the digest and formats them correct.
     * 
     * @param bytes the raw bytes from the digest.
     * @return the formatted bytes.
     */
    private String getFormattedText(byte[] bytes) {
        final StringBuilder buf = new StringBuilder(bytes.length * 2);

        for (int j = 0; j < bytes.length; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    public final void setCharacterEncoding(final String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }
}

