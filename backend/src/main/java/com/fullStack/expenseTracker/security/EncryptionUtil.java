package com.fullStack.expenseTracker.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class EncryptionUtil {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    // Encrypts the data before storing it in the database.
    // Ensures sensitive data is not stored in plain text.
    public static String encryptData(String data) throws Exception {
        SecretKey secretKey = generateSecretKey();
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypts the encrypted data received from the client
    // to retrieve the original information.
    public static String decryptData(String encryptedData) throws Exception {
        SecretKey secretKey = generateSecretKey();
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }

    // Generates a new SecretKey for encryption/decryption.
    private static SecretKey generateSecretKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(128); // Key size
        return keyGenerator.generateKey();
    }
}
