package com.SM.AUTH.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.SM.AUTH.entity.UserAuth;

@Repository
public interface UserAuthRepository extends MongoRepository<UserAuth, Long> {
	Optional<UserAuth> findByEmail(String email);

    Optional<UserAuth> findByUsername(String username);

    Optional<UserAuth> findByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByPhone(String phone);

    Optional<UserAuth> findFirstByOrderByIdDesc();
}
