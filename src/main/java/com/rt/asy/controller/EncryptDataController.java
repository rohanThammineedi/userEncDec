package com.rt.asy.controller;

import com.rt.asy.service.EncryptKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/encrypt")
public class EncryptDataController {

    @Autowired
    private EncryptKeyService encryptKeyService;

    @PostMapping("/data")
    public String encryptData(@RequestBody String data) {
        return encryptKeyService.encrypt(data);
    }
}
