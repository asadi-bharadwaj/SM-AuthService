package com.SM.AUTH.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.SM.AUTH.entity.RefreshToken;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String token);

}