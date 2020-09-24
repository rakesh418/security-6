package com.lex418.security6.repositories;

import com.lex418.security6.entities.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp,Integer> {
    Optional<Otp> findOtpByUsername(String otp);
}
