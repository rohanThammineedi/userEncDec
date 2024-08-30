package com.mgen.pgen.encryption.utils;

import com.mgen.pgen.encryption.data.dto.UserRequestDTO;
import com.mgen.pgen.encryption.data.dto.UserResponseDTO;
import com.mgen.pgen.encryption.data.entity.UserEntity;

public class UserUtils {

    public static UserEntity dtoToEntity(UserRequestDTO userRequestDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRequestDTO.getUsername());
        userEntity.setPassword(userRequestDTO.getPassword());
        userEntity.setEmail(userRequestDTO.getEmail());
        userEntity.setRoles(userRequestDTO.getRoles());
        return userEntity;
    }

    public static UserResponseDTO entityToDto(UserEntity userEntity) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(userEntity.getId());
        userResponseDTO.setUsername(userEntity.getUsername());
        userResponseDTO.setEmail(userEntity.getEmail());
        userResponseDTO.setRoles(userEntity.getRoles());
        return userResponseDTO;
    }
}
