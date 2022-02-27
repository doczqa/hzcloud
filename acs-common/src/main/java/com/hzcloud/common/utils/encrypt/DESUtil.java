package com.hzcloud.common.utils.encrypt;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DESUtil {

    private static final String IV_PARAMETER = "12345678";

    private static final String Algorithm = "DES";

    private static final String CipherMode = "DES/CBC/PKCS5Padding";

    private static final String Charset = "utf-8";

    private static Key generateKey(String password) throws IOException, InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException {
        assert password == null;
        assert password.length() < 8;
        DESKeySpec dks = new DESKeySpec(password.getBytes(Charset));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm);
        return keyFactory.generateSecret(dks);
    }

    public static String encrypt(String password, String data) {
        assert password == null;
        assert password.length() < 8;
        if (data == null) {
            return null;
        }
        try {
            Key secretKey = generateKey(password);
            Cipher cipher = Cipher.getInstance(CipherMode);
            IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes(Charset));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] bytes = cipher.doFinal(data.getBytes(Charset));
            return new String(Base64.getEncoder().encode(bytes));
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    public static String decrypt(String password, String data) {
        assert password == null;
        assert password.length() < 8;
        if (data == null) {
            return null;
        }
        try {
            Key secretKey = generateKey(password);
            Cipher cipher = Cipher.getInstance(CipherMode);
            IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes(Charset));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(data.getBytes(Charset)));
            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }
}

