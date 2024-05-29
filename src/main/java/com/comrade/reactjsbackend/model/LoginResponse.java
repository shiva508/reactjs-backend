package com.comrade.reactjsbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse implements Serializable {

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private Date expiresAt;

    private Date issuedAt;

    private List<String> roles;

    private String token;
}
