package com.zakariarachida.jwt.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zakariarachida.jwt.api.entity.AuthRequest;
import com.zakariarachida.jwt.api.util.JwtUtil;

@RestController
public class WelcomeController {

	@Autowired
	JwtUtil jwtUtil;
    @Autowired
    AuthenticationManager authenticationManager;
  

    @GetMapping("/")
    public String welcome(){
        return "Welcome to JWT EXAMPLE !!!";
    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {


        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        }catch(Exception e){
            throw new Exception("invalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUserName());
    }
}
