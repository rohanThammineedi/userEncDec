package com.mgen.pgen.encryption.service;

import com.mgen.pgen.encryption.data.dto.UserRequestDTO;
import com.mgen.pgen.encryption.data.dto.UserResponseDTO;
import com.mgen.pgen.encryption.data.entity.UserEntity;
import com.mgen.pgen.encryption.data.repo.UserRepository;
import com.mgen.pgen.encryption.exception.UserGlobalException;
import com.mgen.pgen.encryption.utils.UserUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) {
        UserEntity userEntity = UserUtils.dtoToEntity(userRequestDTO);
        UserEntity saved = null;
        try {
            saved = userRepository.save(userEntity);
            return UserUtils.entityToDto(saved);
        } catch (UserGlobalException e) {
            throw new UserGlobalException("Failed to save user");
        }
    }

    public UserResponseDTO getUser(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserGlobalException("User not found"));
        return UserUtils.entityToDto(userEntity);
    }

    public List<UserResponseDTO> getAllUsers() {
        Iterable<UserEntity> userEntities = null;
        try {
            userEntities = userRepository.findAll();
            return StreamSupport.stream(userEntities.spliterator(), false)
                    .map(UserUtils::entityToDto)
                    .collect(Collectors.toList());
        } catch (UserGlobalException e) {
            throw new UserGlobalException("Failed to get all users");
        }
    }

    public String deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return "User deleted successfully";
        } catch (UserGlobalException e) {
            throw new UserGlobalException("Failed to delete user");
        }
    }

    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO, Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserGlobalException("User not found"));
        userEntity.setUsername(userRequestDTO.getUsername());
        userEntity.setPassword(userRequestDTO.getPassword());
        userEntity.setEmail(userRequestDTO.getEmail());
        userEntity.setRoles(userRequestDTO.getRoles());
        UserEntity updated = null;
        try {
            updated = userRepository.save(userEntity);
            return UserUtils.entityToDto(updated);
        } catch (UserGlobalException e) {
            throw new UserGlobalException("Failed to update user");
        }
    }
}
