package com.SM.AUTH.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.SM.AUTH.entity.UserAuth;

@Repository
public interface UserAuthRepository {

	 Optional<UserAuth> findByEmail(String email);

	    boolean existsByEmail(String email);

	    boolean existsByUsername(String username);
}
