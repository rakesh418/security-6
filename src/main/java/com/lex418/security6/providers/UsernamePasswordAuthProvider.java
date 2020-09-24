package com.lex418.security6.providers;

import com.lex418.security6.authentication.UsernamePasswordAuthentication;
import com.lex418.security6.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsernamePasswordAuthProvider implements AuthenticationProvider {

    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails user= jpaUserDetailsService.loadUserByUsername(username);
        if(user!=null){
            if(passwordEncoder.matches(password,user.getPassword())){
                return new UsernamePasswordAuthentication(username,password, user.getAuthorities());
            }
        }
        throw new BadCredentialsException("bad Credential");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthentication.class.equals(authentication);
    }
}
