package com.kaizensoftware.visitstory.common.config.security.userdetails;

import com.kaizensoftware.visitstory.app.persistence.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;

@Transactional
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepo userRepo;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        return this.userRepo.findByEmail(email)
                .map(CustomUserDetails::new).orElseThrow(() ->
                        new UsernameNotFoundException(email));
    }
    
}
