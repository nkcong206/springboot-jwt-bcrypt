package com.example.JWT;

import com.example.JWT.models.ChangePassword;
import com.example.JWT.repositories.UserRepository;
import com.example.JWT.entities.MyUser;
import com.example.JWT.models.LoginRequest;
import com.example.JWT.models.LoginResponse;
import com.example.JWT.services.JwtUtil;
import com.example.JWT.services.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class helloController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyUserDetailService userDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/hello")
    public String hello() {
        return "Correct username password. Welcome";
    }

    @RequestMapping(value = "/goodbye", method = RequestMethod.GET)
    public String goodbye() {
        return "Welcome from hello1";
    }

    @PostMapping("/create")
    public @ResponseBody
    Boolean createUser(@RequestBody MyUser myUser) {
        MyUser checkuser = userRepository.findByUsername(myUser.getUsername());
        if (checkuser == null) {
            try {
                myUser.setPassword(bCryptPasswordEncoder.encode(myUser.getPassword()));
                userRepository.save(myUser);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            final UserDetails userDetails = userDetailService.checkByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
            final String jwt = jwtUtil.generateToken(userDetails);
            LoginResponse loginResponse = new LoginResponse(jwt, "Login thanh cong");
            return ResponseEntity.status(200).body(loginResponse);
        } catch (UsernameNotFoundException error) {
            LoginResponse loginResponse = new LoginResponse(error.getMessage());
            return ResponseEntity.status(500).body(loginResponse);
        } catch (BadCredentialsException error) {
            LoginResponse loginResponse = new LoginResponse(error.getMessage());
            return ResponseEntity.status(500).body(loginResponse);
        }
    }

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    @PostMapping("/update")
    public @ResponseBody
    Boolean updateUser(@RequestBody ChangePassword changePassword) {
        try {
            final UserDetails userDetails = userDetailService.checkByUsernameAndPassword(changePassword.getUsername(), changePassword.getOld_password());
            MyUser myUser = userRepository.findByUsername(userDetails.getUsername());
            myUser.setPassword(bCryptPasswordEncoder.encode(changePassword.getNew_password()));
            userRepository.save(myUser);
            return true;
        } catch (UsernameNotFoundException error) {
            return false;
        } catch (BadCredentialsException error) {
            return false;
        }
    }



}
