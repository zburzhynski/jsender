package com.zburzhynski.jsender.impl.util;

import com.zburzhynski.jsender.api.exception.EncryptionException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class contains methods for encoding and decoding strings.
 * <p/>
 * Date: 04.04.13
 *
 * @author Vladimir Zburzhynski
 */
public class CryptoUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoUtils.class);

    private static final String ALGORITHM = "AES";

    private static final byte[] KEY_VALUE = new byte[]{'Z', 'b', 'u', 'R', 'z', 'h', 'y',
        'N', 's', 'k', 'i', 'K', 'e', 'y', '2', '2'};

    private static final String ENCODING = "UTF-8";

    private static final String ERROR_MESSAGE = "Cannot get array of byte";

    private CryptoUtils() {
    }

    /**
     * Encode string to MD5 hash.
     *
     * @param source source string to encode
     * @return string in MD5 hash
     */
    public static String encode(String source) {
        String result = StringUtils.EMPTY;
        try {
            byte[] bytes = source.getBytes(ENCODING);
            result = DigestUtils.md5DigestAsHex(bytes);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(ERROR_MESSAGE, e);
        }
        return result;
    }

    /**
     * Encrypts string.
     *
     * @param data data to encrypt
     * @return encrypted string
     * @throws EncryptionException if any
     */
    public static String encrypt(String data) throws EncryptionException {
        try {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = cipher.doFinal(data.getBytes());
            return new Base64().encodeAsString(encVal).replaceAll("(\\r|\\n)", "");
        } catch (Exception e) {
            throw new EncryptionException();
        }
    }

    /**
     * Decrypts string.
     *
     * @param encryptedData encrypted string
     * @return decrypted string
     * @throws EncryptionException if any
     */
    public static String decrypt(String encryptedData) throws EncryptionException {
        try {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedValue = new Base64().decode(encryptedData);
            String decrypted = new String(cipher.doFinal(decodedValue), ENCODING);
            return StringUtils.isNotBlank(decrypted) ? decrypted : null;
        } catch (Exception e) {
            throw new EncryptionException();
        }
    }

    /**
     * Decrypts integer.
     *
     * @param encryptedData encrypted string
     * @return decrypted integer
     * @throws EncryptionException if any
     */
    public static Integer decryptInt(String encryptedData) throws EncryptionException {
        try {
            String decrypted = decrypt(encryptedData);
            return decrypted != null ? Integer.parseInt(decrypted) : null;
        } catch (Exception e) {
            throw new EncryptionException();
        }
    }

    /**
     * Decrypts date.
     *
     * @param encryptedData encrypted string
     * @param template      template
     * @return decrypted date
     * @throws EncryptionException if any
     */
    public static Date decryptDate(String encryptedData, String template) throws EncryptionException {
        try {
            String decrypted = decrypt(encryptedData);
            return decrypted != null ? new SimpleDateFormat(template).parse(decrypted) : null;
        } catch (Exception e) {
            throw new EncryptionException();
        }
    }

    /**
     * Generates key.
     *
     * @return key
     */
    private static Key generateKey() {
        Key key = new SecretKeySpec(KEY_VALUE, ALGORITHM);
        return key;
    }

}
