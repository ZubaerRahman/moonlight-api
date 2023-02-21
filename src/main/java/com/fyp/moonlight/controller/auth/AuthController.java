package com.fyp.moonlight.controller.auth;

import com.fyp.moonlight.exceptions.InvalidUsernamePasswordException;
import com.fyp.moonlight.model.User;
import com.fyp.moonlight.model.security.JwtRequest;
import com.fyp.moonlight.model.security.JwtResponse;
import com.fyp.moonlight.security.JWTUtility;
import com.fyp.moonlight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) throws Exception {
        if (user.getEmail() != null && user.getPassword() != null) {
            if (!userService.doesUserExist(user.email)) {
                userService.saveUser(user.getEmail(), user.getPassword());
                return ResponseEntity.ok().body("Created user");
            } else return ResponseEntity.badRequest().body("User already exists");
        } else return ResponseEntity.badRequest().body("Some attributes are null");
    }

    @PostMapping("/authenticate")
    public JwtResponse login(@RequestBody JwtRequest jwtRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getEmail(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new InvalidUsernamePasswordException("Invalid username");
        }

        final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getEmail());

        final String token = jwtUtility.generateToken(userDetails);

        return new JwtResponse(token);
    }

    @GetMapping("/testLogin")
    public ResponseEntity<String> testUserLoggedIn(Authentication authentication) throws Exception {
        return ResponseEntity.ok().body("Ok");
    }

    @GetMapping("/current")
    public String getCurrentUser(Authentication authentication) {
        System.out.println(authentication.getName());
        System.out.println("-----------------");
        return "";
    }

}
