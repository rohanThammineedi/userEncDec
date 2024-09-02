package com.mgen.pgen.encryption.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgen.pgen.encryption.data.dto.UserApplicationResponse;
import com.mgen.pgen.encryption.data.dto.UserRequestDTO;
import com.mgen.pgen.encryption.data.dto.UserResponseDTO;
import com.mgen.pgen.encryption.exception.UserGlobalException;
import com.mgen.pgen.encryption.service.UserService;
import com.mgen.pgen.encryption.utils.KeyUtils;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    private final KeyUtils keyUtils;

    public UserController(UserService userService, KeyUtils keyUtils) {
        this.userService = userService;
        this.keyUtils = keyUtils;
    }

    @PostMapping("/save")
    public UserApplicationResponse<UserResponseDTO> saveUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        log.info("Received request: {}", userRequestDTO);
        try {
            String decryptedData = keyUtils.decrypt(userRequestDTO.getEncryptedPassword());

            // Parse the JSON and extract the password
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> decryptedMap = objectMapper.readValue(decryptedData, Map.class);
            String decryptedPassword = decryptedMap.get("data");

            userRequestDTO.setPassword(decryptedPassword);
        } catch (Exception e) {
            log.error("Error during decryption: {}", e.getMessage());
            throw new UserGlobalException("Failed to decrypt password");
        }
        UserApplicationResponse<UserResponseDTO> userApplicationResponse = new UserApplicationResponse<>();
        UserResponseDTO saved = userService.saveUser(userRequestDTO);
        userApplicationResponse.setResponse(saved);
        log.info("User saved: {}", saved);
        return userApplicationResponse;
    }


    @PutMapping("/update/{id}")
    public UserApplicationResponse<UserResponseDTO> updateUser(@RequestBody @Valid UserRequestDTO userRequestDTO, @PathVariable Long id) {
        UserApplicationResponse<UserResponseDTO> userApplicationResponse = new UserApplicationResponse<>();
        UserResponseDTO updated = userService.updateUser(userRequestDTO, id);
        userApplicationResponse.setResponse(updated);
        return userApplicationResponse;
    }

    @GetMapping("/getAll")
    public UserApplicationResponse<List<UserResponseDTO>> getAllUsers() {
        UserApplicationResponse<List<UserResponseDTO>> userApplicationResponse = new UserApplicationResponse<>();
        List<UserResponseDTO> allUsers = userService.getAllUsers();
        userApplicationResponse.setResponse(allUsers);
        return userApplicationResponse;
    }

    @GetMapping("/getById/{id}")
    public UserApplicationResponse<UserResponseDTO> getUser(@PathVariable Long id) {
        if (id == null) {
            throw new UserGlobalException("Id cannot be null");
        }
        UserApplicationResponse<UserResponseDTO> userApplicationResponse = new UserApplicationResponse<>();
        UserResponseDTO user = userService.getUser(id);
        userApplicationResponse.setResponse(user);
        return userApplicationResponse;
    }

    @DeleteMapping("/delete/{id}")
    public UserApplicationResponse<String> deleteUser(@RequestBody @PathVariable Long id) {
        UserApplicationResponse<String> userApplicationResponse = new UserApplicationResponse<>();
        String message = userService.deleteUser(id);
        userApplicationResponse.setResponse(message);
        return userApplicationResponse;
    }
}
