package com.mgen.pgen.encryption.controller;

import com.mgen.pgen.encryption.utils.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/keys")
public class KeyController {

    @Autowired
    private KeyUtils keyUtils;

    @GetMapping("/publicKey")
    public String publicKey() {
        return keyUtils.getPublicKey();
    }
}
