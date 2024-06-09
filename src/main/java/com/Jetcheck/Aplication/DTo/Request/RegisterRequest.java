package com.Jetcheck.Aplication.DTo.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    String id;
    String username;
    String password;
    String name;
    String lastname;
    String city;
    String email;
    int profile;
}
