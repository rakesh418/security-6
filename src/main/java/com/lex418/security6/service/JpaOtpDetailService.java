package com.lex418.security6.service;

import com.lex418.security6.entities.Otp;
import com.lex418.security6.repositories.OtpRepository;
import com.lex418.security6.security.SecurityOtp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaOtpDetailService implements UserDetailsService {

    @Autowired
    private OtpRepository otpRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Otp> otp = otpRepository.findOtpByUsername(username);
        SecurityOtp securityOtp = new SecurityOtp(otp.get());
        return securityOtp;
    }
}
