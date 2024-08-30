package com.mgen.pgen.encryption.data.repo;

import com.mgen.pgen.encryption.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
