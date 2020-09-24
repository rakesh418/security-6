package com.lex418.security6.filters;

import com.lex418.security6.authentication.OtpAuthentication;
import com.lex418.security6.authentication.UsernamePasswordAuthentication;
import com.lex418.security6.entities.Otp;
import com.lex418.security6.repositories.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@Component
public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private OtpRepository otpRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        //Step1: username and password
        //Step2: username & otp
        int count=0;

        var username = httpServletRequest.getHeader("username");
        var password = httpServletRequest.getHeader("password");
        var otp = httpServletRequest.getHeader("otp");

        if(otp==null){
            //step 1
            Authentication a = new UsernamePasswordAuthentication(username,password);
            authenticationManager.authenticate(a);

            //generate an OTP
            String code = String.valueOf(new Random().nextInt(9999)+1000);
            Otp otpEntity = new Otp();
            otpEntity.setId(1);
            otpEntity.setUsername(username);
            otpEntity.setOtp(code);
//            otpEntity.setId(count+1);
            otpRepository.save(otpEntity);

        }else{
            //step 2
            Authentication a = new OtpAuthentication(username,otp);
            authenticationManager.authenticate(a);
            //Issue Token
            httpServletResponse.setHeader("Authorization", String.valueOf(UUID.randomUUID()));
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }
}
