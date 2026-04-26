package com.SM.AUTH.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SM.AUTH.entity.OtpVerification;


@Repository
public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long> {

	Optional<OtpVerification> findTopByTargetOrderByIdDesc(String target);
}