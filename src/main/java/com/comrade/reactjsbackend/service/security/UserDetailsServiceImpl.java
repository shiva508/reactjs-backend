package com.comrade.reactjsbackend.service.security;

import com.comrade.reactjsbackend.entity.UserEntity;
import com.comrade.reactjsbackend.model.AuthUser;
import com.comrade.reactjsbackend.model.exception.RecordNotFoundException;
import com.comrade.reactjsbackend.repository.UserRepository;
import com.comrade.reactjsbackend.util.DhaConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Request ");
       UserEntity userEntity = userRepository.findByEmail(username)
                                             .orElseThrow(()->new RecordNotFoundException(String.format(DhaConstants.RECORD_NOT_FOUND, username)));
        log.info("{}",userEntity);
        return new AuthUser(userEntity);
    }
}
