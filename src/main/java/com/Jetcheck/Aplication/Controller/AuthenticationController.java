package com.Jetcheck.Aplication.Controller;

import com.Jetcheck.Aplication.DTo.AuthenticationRequest;
import com.Jetcheck.Aplication.DTo.AuthenticationResponse;
import com.Jetcheck.Aplication.DTo.RecoverRequest;
import com.Jetcheck.Aplication.DTo.RegisterRequest;
import com.Jetcheck.Aplication.Excepcetion.PersonExceptions;
import com.Jetcheck.Aplication.Services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        try{
            return ResponseEntity.ok(service.register(request));
        }catch (PersonExceptions e){
            AuthenticationResponse errorResponse = AuthenticationResponse.builder()
                    .Token(null)
                    .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }

    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
    @PutMapping(value = "recoverypassword")
    public ResponseEntity<String> recovery(@RequestBody RecoverRequest request){
        try {
            return ResponseEntity.ok(service.recoverpassword(request));
        }catch (PersonExceptions e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
