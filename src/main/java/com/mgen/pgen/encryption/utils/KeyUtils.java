package com.mgen.pgen.encryption.utils;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;
import java.util.regex.Pattern;

@Component
@Slf4j
public class KeyUtils {

    private static final String RSA = "RSA";
    public PublicKey publicKey;
    public PrivateKey privateKey;

    public String getPublicKey() {
        String publicKey = Base64.getEncoder().encodeToString(this.publicKey.getEncoded());
        log.info("PUBLIC KEY STRING : {}", publicKey);
        return publicKey;
    }

    @PostConstruct
    public void init() {
        KeyPair keyPair = generateKeyPair();
        this.publicKey = keyPair.getPublic();
        this.privateKey = keyPair.getPrivate();

        log.info("PUBLIC KEY GENERATED : {}", publicKey);
        log.info("=============================================================================");
        log.info("PRIVATE KEY GENERATED : {}", privateKey);
    }

    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate key pair", e);
        }
    }

    public String decrypt(String encryptedData) {
        if (encryptedData == null || encryptedData.isEmpty()) {
            throw new RuntimeException("Encrypted data cannot be null or empty");
        }
        if (!isBase64(encryptedData)) {
            throw new RuntimeException("Encrypted data is not in base64 format");
        }
        try {
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt data", e);
        }
    }

    private boolean isBase64(String str) {
        String base64Pattern = "^[A-Za-z0-9+/]+={0,2}$";
        Pattern pattern = Pattern.compile(base64Pattern);
        return pattern.matcher(str).matches();
    }
}