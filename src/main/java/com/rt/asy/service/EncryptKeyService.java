package com.rt.asy.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
@Slf4j
public class EncryptKeyService {

    private static final String USER_APPLICATION_LINK = "http://localhost:8080/keys/publicKey";
    private static final String RSA = "RSA";

    private static PublicKey PUBLIC_KEY = null;

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        try {
            String publicKeyString = restTemplate.getForObject(USER_APPLICATION_LINK, String.class);
            log.info("Received Public Key String: {}", publicKeyString);

            // Convert the Base64 encoded public key string to a PublicKey object
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PUBLIC_KEY = keyFactory.generatePublic(keySpec);
            log.info("Public key : {}", PUBLIC_KEY);
        } catch (Exception e) {
            log.error("Error initializing public key: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, PUBLIC_KEY);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            log.error("ERROR : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}