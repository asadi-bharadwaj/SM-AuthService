package com.SM.AUTH.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.SM.AUTH.entity.OtpVerification;


@Repository
public interface OtpVerificationRepository extends MongoRepository<OtpVerification, String> {

	Optional<OtpVerification> findTopByTargetOrderByIdDesc(String target);

}