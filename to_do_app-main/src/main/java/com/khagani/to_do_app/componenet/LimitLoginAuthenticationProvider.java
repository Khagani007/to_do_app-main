package com.khagani.to_do_app.componenet;

import com.khagani.to_do_app.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;



@Slf4j
public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {
    public LimitLoginAuthenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService service) {
        setPasswordEncoder(passwordEncoder);
        setUserDetailsService(service);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            Authentication authenticated = super.authenticate(authentication);
            resetAttempts(authenticated.getName());
            return authenticated;
        } catch (BadCredentialsException exception) {
            increaseAttemptCount(authentication.getName());
            throw exception;
        }
    }

    private void resetAttempts(String username) {
        UserServiceImpl userDetailsService = (UserServiceImpl)getUserDetailsService();
        userDetailsService.resetAttempts(username);

    }

    private void increaseAttemptCount(String username) {
        UserServiceImpl userDetailsService = (UserServiceImpl)getUserDetailsService();
        userDetailsService.increaseAttemptCount(username);
    }

}
