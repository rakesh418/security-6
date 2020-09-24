package com.lex418.security6.providers;

import com.lex418.security6.authentication.OtpAuthentication;
import com.lex418.security6.repositories.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OtpAuthProvider implements AuthenticationProvider {

    @Autowired
    private OtpRepository otpRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username=authentication.getName();
        String otp = authentication.getCredentials().toString();

        var o= otpRepository.findOtpByUsername(username);
        if(o.isPresent()){
            return new OtpAuthentication(username,otp, List.of(()->"read"));
        }
        throw new BadCredentialsException("Bad OTP credential");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthentication.class.equals(authentication);
    }
}
