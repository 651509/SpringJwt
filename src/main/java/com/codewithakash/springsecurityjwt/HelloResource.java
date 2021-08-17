package com.codewithakash.springsecurityjwt;

import com.codewithakash.springsecurityjwt.model.AuthenticationRequest;
import com.codewithakash.springsecurityjwt.model.AuthenticationResponse;
import com.codewithakash.springsecurityjwt.service.MyUserDetailsService;
import com.codewithakash.springsecurityjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResource {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

   @GetMapping({"/hello"})
    public String Hello(){
        return "Hello World";
    }


    @PostMapping({"/authenticate"})
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticateRequest) throws Exception{

       try {
           authenticationManager.authenticate(
                           new UsernamePasswordAuthenticationToken(authenticateRequest.getUserName(), authenticateRequest.getPassword())
           );
       }
       catch(BadCredentialsException e)
       {
           throw new Exception("Incorrect username or password");
       }

       final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticateRequest.getUserName());

       final String jwt = jwtUtil.generateToken(userDetails);

       return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }
}
