package com.yeva.dauletova.services;

import com.yeva.dauletova.models.MyUserDetails;
import com.yeva.dauletova.models.User;
import com.yeva.dauletova.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user= userRepository.findByEmail(email);
        if(user.isEmpty()){
            System.out.println("incorrect email");
            throw new UsernameNotFoundException("User not found");

        }
        return new MyUserDetails(user.get());
    }

}
