package com.hzcloud.common.utils.encrypt;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public class TripleDESUtil {

    private static final String Algotithm = "DESede";

    private static final String CipherMode = "DESede/ECB/PKCS5Padding";

    private static final String Charset = "utf-8";

    private static Key generateKey(String password) throws IOException, InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException {
        DESedeKeySpec dks = new DESedeKeySpec(password.getBytes(Charset));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algotithm);
        return keyFactory.generateSecret(dks);
    }

    public static String encrypt(String password, String data) {
        assert password == null;
        assert password.length() < 24;
        if (data == null) {
            return null;
        }
        try {
            Key secretKey = generateKey(password);
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] bytes = cipher.doFinal(data.getBytes(Charset));
            return new String(Base64.getEncoder().encode(bytes));
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    public static String decrypt(String password, String data) {
        assert password == null;
        assert password.length() < 24;
        if (data == null) {
            return null;
        }
        try {
            Key secretKey = generateKey(password);
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(data.getBytes(Charset))), Charset);
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }
}