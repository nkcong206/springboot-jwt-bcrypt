package com.example.JWT.services;

import com.example.JWT.entities.MyUser;
import com.example.JWT.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = userRepository.findByUsername(username);
        System.out.println(myUser.getPassword());
        return new User("cong", "123", new ArrayList<>());
    }

    public UserDetails checkByUsernameAndPassword(String username, String password) {
        MyUser myUser = userRepository.findByUsername(username);
        if (myUser == null) {
            throw new UsernameNotFoundException("User name not found");
        }
        if (!bCryptPasswordEncoder.matches(password, myUser.getPassword())) {
            throw new BadCredentialsException("Password incorrect");
        } else {
            return new User(username, myUser.getPassword(), new ArrayList<>());
        }
    }

    public UserDetails signup(String username, String password){
        MyUser newUser = new MyUser(username,password);
        newUser.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(newUser);
        return new User(username, newUser.getPassword(), new ArrayList<>());
    }


}
